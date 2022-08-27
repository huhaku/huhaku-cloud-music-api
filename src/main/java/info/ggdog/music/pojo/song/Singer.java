package info.ggdog.music.pojo.song;

/**
 * @author huhaku
 * @date 2022/5/22
 */
public class Singer {
    private long id;
    private String mid;
    private String name;
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
    public String getMid() {
        return mid;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
