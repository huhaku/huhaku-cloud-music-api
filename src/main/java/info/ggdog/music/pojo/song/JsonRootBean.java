package info.ggdog.music.pojo.song;

/**
 * @author huhaku
 * @date 2022/5/22
 */
public class JsonRootBean {
    private Integer code;
    private Object data;
    public void setCode(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public Object getData() {
        return data;
    }
}
