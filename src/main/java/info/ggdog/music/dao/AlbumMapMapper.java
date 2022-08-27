package info.ggdog.music.dao;

import info.ggdog.music.pojo.Album;
import info.ggdog.music.pojo.AlbumMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author huhaku
 * @date 2022/5/21
 */
@Mapper
@Component
public interface AlbumMapMapper {
    void install(@Param("albumMap") AlbumMap albumMap);

    void deleteAll();
}
