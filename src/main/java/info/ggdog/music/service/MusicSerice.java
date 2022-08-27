package info.ggdog.music.service;

import info.ggdog.music.pojo.song.JsonRootBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author huhaku
 * @date 2022/5/21
 */
public interface MusicSerice {
    /**
     * 刷新目录数据
     */
    void flashCache();

    /**
     * 获取歌单列表
     * @return
     */
    JsonRootBean playSong();

    /**
     * 获取专辑列表
     * @return
     */
    JsonRootBean album();

    /**
     * 歌单,专辑以及搜索列表
     * @param p
     * @param n
     * @param w
     * @return
     */
    JsonRootBean search(Integer p, Integer n, String w);

    /**
     * 获取歌曲链接和歌词
     * @param songmid
     * @return
     */
    JsonRootBean song(String songmid);

    /**
     * 传出歌曲流
     * @param mid
     * @param request
     * @param response
     */
    void getSongStream(String mid, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException;

    /**
     * 获取图片
     * @param type
     * @param name
     * @param request
     * @param response
     */
    void getimg(String type, String name, HttpServletRequest request, HttpServletResponse response);
}
