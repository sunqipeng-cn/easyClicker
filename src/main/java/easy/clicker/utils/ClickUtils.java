package easy.clicker.utils;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @author sunqipeng3
 * @date 2021-02-10 10:33:14
 */
public class ClickUtils {
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void click(int x, int y){
        java.awt.Point p = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mouseMove(p.x, p.y);
    }
}
