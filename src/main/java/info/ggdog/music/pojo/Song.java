package info.ggdog.music.pojo;

/**
 * @author huhaku
 * @date 2022/5/21
 */
public class Song {
    private Long id;
    private String mid;
    private String name;
    private String singer;
    private String album;
    private String cover;
    private String path;
    private Integer num;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Song(String mid, String name, String singer, String album, String cover, String path) {
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.cover = cover;
        this.path = path;
        this.mid = mid;
    }
}
