package info.ggdog.music.dao;

import info.ggdog.music.pojo.SongList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huhaku
 * @date 2022/5/21
 */
@Mapper
@Component
public interface SongListMapper {
    ArrayList<String> findAllName();

    void saveAll(@Param("dirItem") List<Map<String,String>> dirItem);

    Long findByName(String songListName);

    void deleteAll();

    ArrayList<Map<String, Object>> findAll();

    ArrayList<Map<String, Object>> search(@Param("p") Integer p, @Param("n") Integer n, @Param("w") String w);

    void addOne(@Param("w") String w);
}
