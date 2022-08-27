package info.ggdog.music.pojo.song;

import java.util.Date;
import java.util.List;

/**
 * @author huhaku
 * @date 2022/5/22
 */
public class Data {
    private int code;
    private String date;
    private int curnum;
    private int curpage;
    private List<SongList> list;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setCurnum(int curnum) {
        this.curnum = curnum;
    }
    public int getCurnum() {
        return curnum;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }
    public int getCurpage() {
        return curpage;
    }

    public List<SongList> getList() {
        return list;
    }

    public void setList(List<SongList> list) {
        this.list = list;
    }
}
