package info.ggdog.music.pojo;

import java.util.Date;

/**
 * @author huhaku
 * @date 2022/5/21
 */
public class Album {
    private Long id;
    private String name;
    private String img;
    private Date time;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Album(String name, String img, Date time) {
        this.name = name;
        this.img = img;
        this.time = time;
    }
}
