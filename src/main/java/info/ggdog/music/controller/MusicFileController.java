package info.ggdog.music.controller;

import info.ggdog.music.service.MusicSerice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author huhaku
 * @date 2022/5/21
 */
@Controller
@Slf4j
public class MusicFileController {
    @Autowired
    private MusicSerice musicSerice;

    @GetMapping("/getsongstream")
    public void getSongStream(String mid,
                                HttpServletRequest request,
                                HttpServletResponse response) throws UnsupportedEncodingException {
        response.setDateHeader("Expires", System.currentTimeMillis() + 60 * 60 * 1000);
        response.setHeader("Cache-Control", "max-age=3600");
        musicSerice.getSongStream(mid, request, response);
    }

    @GetMapping("/getimg")
    public void getimg(String type,
                         String name,
                         HttpServletRequest request,
                         HttpServletResponse response) throws UnsupportedEncodingException {
        response.setDateHeader("Expires", System.currentTimeMillis() + 60 * 60 * 1000);
        response.setHeader("Cache-Control", "max-age=3600");
        musicSerice.getimg(type, name, request, response);
    }
}
