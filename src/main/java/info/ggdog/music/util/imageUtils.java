package info.ggdog.music.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author huhaku
 * @date 2022/7/27
 */
public class imageUtils {
    /**
     * JAVA 图像等比缩放
     *
     * @param srcImageFile  缩放的图片
     * @param destImageFile 缩放后的图片
     * @return
     */
    public static boolean scale(File srcImageFile, File destImageFile, int width, int height) {
        try {
            //使用ImageIO的read方法读取图片
            BufferedImage read = ImageIO.read(srcImageFile);
            //调用缩放方法获取缩放后的图片
            Image img = read.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            //创建一个新的缓存图片
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //获取画笔
            Graphics2D graphics = image.createGraphics();
            //将Image对象画在画布上,最后一个参数,ImageObserver:接收有关 Image 信息通知的异步更新接口,没用到直接传空
            graphics.drawImage(img, 0, 0, null);
            //一定要释放资源
            graphics.dispose();
            //获取到文件的后缀名
            String fileName = srcImageFile.getName();
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            //使用ImageIO的write方法进行输出
            ImageIO.write(image, formatName, destImageFile);
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("imageUtils err:" + srcImageFile.getPath());
            return false;
        }
        return true;
    }
}
