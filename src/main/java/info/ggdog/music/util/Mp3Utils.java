package info.ggdog.music.util;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author huhaku
 * @date 2022/5/21
 */

public class Mp3Utils {
    public static ID3v2 expMp3File(String path) {
        Mp3File mp3file = null;
        try {
            mp3file = new Mp3File(path);
        } catch (Exception e) {
            return null;
        }
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            return id3v2Tag;
        }
        return null;
    }
}
