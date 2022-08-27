package info.ggdog.music.pojo;

/**
 * @author huhaku
 * @date 2022/5/21
 */
public class SongListMap {
    private Long id;
    private Long songListId;
    private Long songId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSongListId() {
        return songListId;
    }

    public void setSongListId(Long songListId) {
        this.songListId = songListId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public SongListMap(Long songListId, Long songId) {
        this.songListId = songListId;
        this.songId = songId;
    }
}
