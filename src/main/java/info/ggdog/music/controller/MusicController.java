package info.ggdog.music.controller;

import info.ggdog.music.pojo.song.JsonRootBean;
import info.ggdog.music.service.MusicSerice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author huhaku
 * @date 2022/5/21
 */
@RestController
@Slf4j
public class MusicController {
    @Autowired
    private MusicSerice musicSerice;

    @GetMapping("/")
    public String miao() {
        return "喵喵喵!!!";
    }

    @GetMapping("/flashCache")
    public String flashCache() {
        musicSerice.flashCache();
        return "ok";
    }

    @GetMapping(value = "/playSong", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JsonRootBean playSong() {
        return musicSerice.playSong();
    }

    @GetMapping(value = "/album", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JsonRootBean album() {
        return musicSerice.album();
    }

    @GetMapping(value = "/search", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JsonRootBean search(Integer p, Integer n, String w) {
        return musicSerice.search(p, n, w);
    }

    @GetMapping(value = "/song", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JsonRootBean song(String songmid, Integer guid, String lyric) {
        return musicSerice.song(songmid);
    }
}
