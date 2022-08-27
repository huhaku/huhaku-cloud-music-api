package info.ggdog.music.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpatric.mp3agic.ID3v2;
import info.ggdog.music.dao.*;
import info.ggdog.music.pojo.Album;
import info.ggdog.music.pojo.AlbumMap;
import info.ggdog.music.pojo.Song;
import info.ggdog.music.pojo.SongListMap;
import info.ggdog.music.pojo.song.Data;
import info.ggdog.music.pojo.song.JsonRootBean;
import info.ggdog.music.pojo.song.Singer;
import info.ggdog.music.service.MusicSerice;
import info.ggdog.music.util.Mp3Utils;
import info.ggdog.music.util.imageUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @author huhaku
 * @date 2022/5/21
 */
@Service
@Slf4j
public class MusicSericeImpl implements MusicSerice {

    private static boolean lock;

    @Value("${music.url}")
    private String musicUrl;

    @Value("${music.pash}")
    private String musicPath;

    @Value("${lrc.cache:false}")
    private String lrcCahe;

    @Value("${music.api}")
    private String musicApi;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongListMapper songListMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AlbumMapMapper albumMapMapper;

    @Autowired
    private SongListMapMapper songListMapMapper;

    /**
     * 刷新目录数据
     */
    @Override
    @Transactional
    public void flashCache() {
        synchronized (this) {
            if (lock) {
                log.info("歌单导入锁");
                return;
            }
            lock = true;
        }
        try {
            if (StrUtil.isEmpty(musicPath)) {
                throw new RuntimeException("musicPash 为空");
            }
            deleteDB();
            log.info("删除旧数据完成");

            HashSet<Map<String, String>> dir = new HashSet<>();
            HashSet<String> mp3Files = new HashSet<>();
            checkDiv(dir, mp3Files);
            List<Map<String, String>> dirItem = new ArrayList<>(dir);
            //插入歌单
            songListMapper.saveAll(dirItem);
            log.info("插入歌单完成");
            saveSong(mp3Files);
            log.info("插入歌曲完成");
        } finally {
            lock = false;
        }
    }

    /**
     * 获取歌单列表
     *
     * @return
     */
    @Override
    public JsonRootBean playSong() {
        ArrayList<Map<String, Object>> allSongList = songListMapper.findAll();
        JsonRootBean jsonRootBean = getJsonRootBean(allSongList, "songList");
        return jsonRootBean;
    }

    /**
     * 获取专辑列表
     *
     * @return
     */
    @Override
    public JsonRootBean album() {
        ArrayList<Map<String, Object>> allSongList = albumMapper.findAll();
        JsonRootBean jsonRootBean = getJsonRootBean(allSongList, "album");
        return jsonRootBean;
    }

    /**
     * 歌单,专辑以及搜索列表
     *
     * @param p
     * @param n
     * @param w
     * @return
     */
    @Override
    public JsonRootBean search(Integer p, Integer n, String w) {
        if (p <= 0) {
            p = 1;
        }
        if (n <= 50) {
            n = 50;
        }
        p = (p - 1) * n;
        //歌单
        ArrayList<Map<String, Object>> allSongList = null;
        if (w.contains("【songList】")) {
            w = w.replace("【songList】", "");
            allSongList = songListMapper.search(p, n, w);
            songListMapper.addOne(w);
        }
        //专辑
        else if (w.contains("【album】")) {
            w = w.replace("【album】", "");
            allSongList = albumMapper.search(p, n, w);
            albumMapper.addOne(w);
        }
        //普通搜索
        else {
            allSongList = songMapper.search(p, n, w);
        }
        JsonRootBean jsonRootBean = getJsonRootBean(allSongList, "song", p);
        return jsonRootBean;
    }

    /**
     * 获取歌曲链接和歌词
     *
     * @param songmid
     * @return
     */
    @Override
    public JsonRootBean song(String songmid) {
        Map<String, Object> song = songMapper.findSongByMid(songmid);
        if (null == song) {
            return new JsonRootBean();
        }
        songMapper.addOne(songmid);
        JsonRootBean jsonRootBean = new JsonRootBean();
        HashMap<String, String> resMap = new HashMap<>();
        resMap.put("vkey", songmid);
        resMap.put("musicUrl", musicUrl + "/getsongstream?mid=" + songmid);
        String path = String.valueOf(song.get("path")).replace(".mp3", ".lrc");
        String lrc = "[00:00:00] 暂无歌词或纯音乐";
        if (FileUtil.isFile(path)) {
            lrc = FileUtil.readUtf8String(path);
        } else {
            if ("true".equalsIgnoreCase(lrcCahe)) {
                lrc = getLrc(song, path, lrc);
            }
        }
        resMap.put("lyric", lrc);
        jsonRootBean.setData(resMap);
        return jsonRootBean;
    }

    private String getLrc(Map<String, Object> song, String path, String lrc) {
        try {
            String slrc = null;
            String search = HttpUtil.get(musicApi + "/search?realIP=116.25.146.177&keywords=" + String.valueOf(song.get("name")) + String.valueOf(song.get("singer")), 6000);
            if (StrUtil.isNotEmpty(search)) {
                Integer id = JSON.parseObject(search).getJSONObject("result").getJSONArray("songs").getJSONObject(0).getInteger("id");
                search = HttpUtil.get(musicApi + "/lyric?realIP=116.25.146.177&id=" + id, 6000);
                slrc = JSON.parseObject(search).getJSONObject("lrc").getString("lyric");
            }

            if (StrUtil.isNotEmpty(slrc)) {
                lrc = slrc.replace("\n", "\r").replace("\n\r", "\r").replace("\r\n", "\r");
                log.info("{}远程歌词抓取成功", String.valueOf(song.get("name")) + String.valueOf(song.get("singer")));
                File file = FileUtil.writeString(lrc, path, CharsetUtil.CHARSET_UTF_8);
                if (file.isFile()) {
                    log.info("歌词文件{}写入成功", path);
                }
            }
        } catch (Exception e) {
            log.info("远程歌词抓取失败,{},{}", musicApi + "/search?realIP=116.25.146.177&keywords=" + String.valueOf(song.get("name")) + String.valueOf(song.get("singer")), e.getMessage());
        }
        return lrc;
    }

    /**
     * 传出歌曲流
     *
     * @param mid
     * @param request
     * @param response
     */
    @Override
    public void getSongStream(String mid, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String, Object> song = songMapper.findSongByMid(mid);
        if (null == song) {
            return;
        }
        String fileName = String.valueOf(song.get("name")) + ".mp3";//下载的文件名
        String realPath = String.valueOf(song.get("path"));
        try {
            pushData(response, fileName, realPath);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return;
    }

    private void pushData(HttpServletResponse response, String fileName, String realPath) throws UnsupportedEncodingException {

        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
            File file = new File(realPath);
            // 如果文件名存在，则进行下载
            if (file.exists()) {

                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
//                    log.info("Download the song successfully!" + realPath);
                } catch (Exception e) {
                    log.error("Download the file failed!" + realPath);
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取图片
     *
     * @param type
     * @param name
     * @param request
     * @param response
     */
    @Override
    public void getimg(String type, String name, HttpServletRequest request, HttpServletResponse response) {
        String path = null;
        if ("songList".equals(type)) {
            path = musicPath + File.separator + name + File.separator + "index.jpg";
        }
        if ("album".equals(type)) {
            path = albumMapper.fildImgPashByName(name);
        }

        if ("song".equals(type)) {
            path = songMapper.fildImgPashByName(name);
        }

        if (StrUtil.isEmpty(path) || !FileUtil.isFile(path)) {
            path = musicPath + File.separator + "default.jpg";
        }
        try {
            pushData(response, "index.jpg", path);
            return;
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }


    private JsonRootBean getJsonRootBean(ArrayList<Map<String, Object>> allSongList, String type) {
        return getJsonRootBean(allSongList, type, 1);
    }

    @NotNull
    private JsonRootBean getJsonRootBean(ArrayList<Map<String, Object>> allSongList, String type, Integer p) {
        JsonRootBean jsonRootBean = new JsonRootBean();
        jsonRootBean.setCode(0);
        Data data = new Data();
        data.setCode(0);
        data.setDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
        data.setCurnum(allSongList.size());
        data.setCurpage(p);
        ArrayList<info.ggdog.music.pojo.song.SongList> songLists = new ArrayList<>();
        for (Map<String, Object> songListx : allSongList) {
            info.ggdog.music.pojo.song.SongList songList = new info.ggdog.music.pojo.song.SongList();
            songList.setSongname(String.valueOf(songListx.get("name")));
            songList.setAlbumname(String.valueOf(songListx.get("album")));
            songList.setSongmid(String.valueOf(songListx.get("mid")));
            Singer singer = new Singer();
            ZoneId zoneId = ZoneId.systemDefault();
            if (songListx.containsKey("time")) {
                LocalDateTime localDateTime = (LocalDateTime) songListx.get("time");
                ZonedDateTime zdt = localDateTime.atZone(zoneId);
                Date date = Date.from(zdt.toInstant());
                singer.setName(DateUtil.format(date, "yyyy-MM-dd"));
            } else {
                singer.setName(String.valueOf(songListx.get("singer")));
            }
            songList.setSinger(singer);
            songList.setAlbumimg(musicUrl + "/getimg?type=" + type + "&name=" + songListx.get("name"));
            songLists.add(songList);
        }

        data.setList(songLists);
        jsonRootBean.setData(data);
        return jsonRootBean;
    }


    private void deleteDB() {
        songMapper.deleteAll();
        songListMapper.deleteAll();
        albumMapper.deleteAll();
        albumMapMapper.deleteAll();
        songListMapMapper.deleteAll();
        FileUtil.clean(musicPath + File.separator + "musicCache");
    }

    /**
     * 新增的歌曲关联歌单和专辑
     *
     * @param mp3Files
     */

    private void saveSong(HashSet<String> mp3Files) {
        List<String> mp3FileItem = new ArrayList<>(mp3Files);

        for (String md3File : mp3FileItem) {
            ID3v2 id3v2 = Mp3Utils.expMp3File(md3File);
            if (null == id3v2) {
                log.error("歌曲{}解析出错", md3File);
                continue;
            }
            String name = FileUtil.getName(md3File).replace(".mp3", "");
            String singer = StrUtil.isEmpty(id3v2.getArtist()) ? "群星" : id3v2.getArtist();
            String album = StrUtil.isEmpty(id3v2.getAlbum()) ? "群星" : id3v2.getAlbum();
            String cover = null;
            String coverMin = null;
            byte[] albumImageData = id3v2.getAlbumImage();
            if (albumImageData != null) {
                StringBuilder albumImagePath = new StringBuilder(musicPath).append(File.separator)
                        .append("musicCache").append(File.separator)
                        .append(SecureUtil.md5(name)).append(".jpg");
                FileUtil.writeBytes(albumImageData, albumImagePath.toString());
                cover = albumImagePath.toString();
                coverMin = cover.replace(".jpg", "_min.jpg");
                imageUtils.scale(new File(cover), new File(coverMin), 200, 200);
            }
            Song song = new Song(SecureUtil.md5(name + album + md3File), name, singer, album, cover, md3File);
            songMapper.insert(song);
            Long albumId = albumMapper.fildByName(album);
            Album album1 = null;
            if (albumId == null) {
                album1 = new Album(album, coverMin, new Date());
                albumMapper.insert(album1);
            }
            // 歌曲关联专辑
            AlbumMap albumMap = new AlbumMap(album1 == null ? albumId : album1.getId(), song.getId());
            albumMapMapper.install(albumMap);
            //歌曲关联歌单
            String songListName = FileUtil.getParent(md3File, 1).replace(musicPath + File.separator, "");
            Long id = songListMapper.findByName(songListName);
            SongListMap songListMap = new SongListMap(id, song.getId());
            songListMapMapper.install(songListMap);
        }
    }


    private void checkDiv(HashSet<Map<String, String>> dir, HashSet<String> mp3File) {
        //获取所有目录
        File[] ls = FileUtil.ls(musicPath);
        for (File pash : ls) {
            String name = pash.getPath().replace(musicPath + File.separator, "");
            if ("musicCache".equals(name)) {
                continue;
            }
            if (FileUtil.isDirectory(pash)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                Date date = new Date(pash.lastModified());
                map.put("time", DateUtil.format(date, "yyyy-MM-dd"));
                dir.add(map);
            }
        }

        List<File> files = FileUtil.loopFiles(musicPath);
        for (File file : files) {
            if ("mp3".equals(FileTypeUtil.getType(file))) {
                mp3File.add(file.getPath());
            }
        }
    }
}
