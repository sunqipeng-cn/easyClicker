package easy.clicker.utils;

import easy.clicker.mod.Mod;
import easy.clicker.model.Img;
import easy.clicker.model.Point;
import easy.clicker.model.ScreenShot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

public class OcrUtils {

    /**
     * 截图
     *
     * @return 返回BufferedImage
     */
    public static void shotScreen(ScreenShot screenShot) {
        BufferedImage bfImage = null;
        try {
            Robot robot = new Robot();
            bfImage = robot.createScreenCapture(new Rectangle(screenShot.getX(), screenShot.getY(), screenShot.getWidth(), screenShot.getHeight()));
//
//            try {
//                ImageIO.write(bfImage, "png", new File("E:\\temp\\easyclicker\\modsss\\a.png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
        screenShot.setRgbData(getImageGRB(bfImage));
    }

    /**
     * Mod 信息
     * @param file mod文件
     */
    public static Mod dynamicMod(File file){
        try(InputStream in = new FileInputStream(file)) {
            BufferedImage bfImage = ImageIO.read(in);
            String modName = file.getName().split("\\.")[0];
            Mod mod = new Mod(modName);
            mod.setHeight(bfImage.getHeight());
            mod.setWidth(bfImage.getWidth());
            mod.setRgbData(getImageGRB(bfImage));
            return mod;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    /**
     * 根据BufferedImage获取图片RGB数组
     *
     * @param bfImage
     * @return
     */
    public static int[][] getImageGRB(BufferedImage bfImage) {
        int width = bfImage.getWidth();
        int height = bfImage.getHeight();
        int[][] result = new int[height][width];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                //使用getRGB(w, h)获取该点的颜色值是ARGB，而在实际应用中使用的是RGB，所以需要将ARGB转化成RGB，即bufImg.getRGB(w, h) & 0xFFFFFF。
                result[h][w] = bfImage.getRGB(w, h) & 0xFFFFFF;
            }
        }
        return result;
    }


    public static Point findPIP(ScreenShot screenShot, Mod mod) {
        int scrHeight = screenShot.getHeight();
        int scrWidth = screenShot.getWidth();

        int modHeight = mod.getHeight();
        int modWidth = mod.getWidth();

        int[][] scrData = screenShot.getRgbData();
        int[][] modData = mod.getRgbData();

        for (int y = 0; y < scrHeight - modHeight; y++) {
            for (int x = 0; x < scrWidth - modWidth; x++) {
                //根据目标图的尺寸，得到目标图四个角映射到屏幕截图上的四个点，
                //判断截图上对应的四个点与图B的四个角像素点的值是否相同，
                //如果相同就将屏幕截图上映射范围内的所有的点与目标图的所有的点进行比较。
                if ((modData[0][0] ^ scrData[y][x]) == 0
                        && (modData[0][modWidth - 1] ^ scrData[y][x + modWidth - 1]) == 0
                        && (modData[modHeight - 1][modWidth - 1] ^ scrData[y + modHeight - 1][x + modWidth - 1]) == 0
                        && (modData[modHeight - 1][0] ^ scrData[y + modHeight - 1][x]) == 0) {

                    boolean allMatch = true;
                    int biggerY = 0;
                    int biggerX = 0;
                    int xor = 0;
                    for (int smallerY = 0; smallerY < modHeight; smallerY++) {
                        biggerY = y + smallerY;
                        for (int smallerX = 0; smallerX < modWidth; smallerX++) {
                            biggerX = x + smallerX;
                            if (biggerY >= scrHeight || biggerX >= scrWidth) {
                                allMatch = false;
                                break;
                            }
                            xor = modData[smallerY][smallerX] ^ scrData[biggerY][biggerX];
                            if (xor != 0) {
                                allMatch = false;
                                break;
                            }
                        }
                        biggerX = x;
                    }


                    //如果比较结果完全相同，则说明图片找到，填充查找到的位置坐标数据到查找结果数组。
                    if (allMatch) {
                        return randomPoint(x, y,screenShot, mod);
                    }
                }
            }
        }


        return null;
    }

    private static Point randomPoint(int x, int y, ScreenShot screenShot, Img mod){
        Random r = new Random(10);
        int w = r.nextInt(mod.getWidth());
        int h = r.nextInt(mod.getHeight());
        int cx = x + w + screenShot.getX();
        int cy = y + h + screenShot.getY();
        return new Point(cx, cy);
    }

    private static Point modCenter(int x, int y, ScreenShot screenShot, Img mod) {
        int cx = x + mod.getWidth() / 2 + screenShot.getX();
        int cy = y + mod.getHeight() / 2 + screenShot.getY();
        return new Point(cx, cy);
    }

}
