package info.ggdog.music.dao;

import info.ggdog.music.pojo.AlbumMap;
import info.ggdog.music.pojo.SongListMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author huhaku
 * @date 2022/5/21
 */
@Mapper
@Component
public interface SongListMapMapper {
    void install(@Param("songListMap") SongListMap songListMap);

    void deleteAll();
}
