package info.ggdog.music.pojo;

import java.util.Date;

/**
 * @author huhaku
 * @date 2022/5/21
 */
public class AlbumMap {
    private Long id;
    private Long albumId;
    private Long songId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public AlbumMap(Long albumId, Long songId) {
        this.albumId = albumId;
        this.songId = songId;
    }
}
