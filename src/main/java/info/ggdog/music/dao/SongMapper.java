package info.ggdog.music.dao;

import info.ggdog.music.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author huhaku
 * @date 2022/5/21
 */
@Mapper
@Component
public interface SongMapper {
    ArrayList<String> findAllFilePath();

    void insert(@Param("song") Song song);

    void deleteAll();

    ArrayList<Map<String,Object>> search(@Param("p") Integer p, @Param("n") Integer n, @Param("w") String w);

    Map<String,Object> findSongByMid(@Param("songmid") String songmid);

    String fildImgPashByName(String name);

    void addOne(String songmid);
}
