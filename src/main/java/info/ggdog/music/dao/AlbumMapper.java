package info.ggdog.music.dao;

import info.ggdog.music.pojo.Album;
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
public interface AlbumMapper {
    Long fildByName(@Param("album") String album);

    void insert(@Param("album") Album album);

    void deleteAll();

    ArrayList<Map<String,Object>> findAll();

    ArrayList<Map<String,Object>> search(@Param("p") Integer p, @Param("n") Integer n, @Param("w") String w);

    String fildImgPashByName(String name);

    void addOne(String w);
}
