package info.ggdog.music.pojo.song;

/**
 * @author huhaku
 * @date 2022/5/22
 */
public class SongList {
    private String curCount;
    private String songname;
    private Singer singer;
    private String albumname;
    private String songmid;
    private String albumimg;

    public String getCurCount() {
        return curCount;
    }

    public void setCurCount(String curCount) {
        this.curCount = curCount;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getSongname() {
        return songname;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setSongmid(String songmid) {
        this.songmid = songmid;
    }

    public String getSongmid() {
        return songmid;
    }

    public void setAlbumimg(String albumimg) {
        this.albumimg = albumimg;
    }

    public String getAlbumimg() {
        return albumimg;
    }

}
