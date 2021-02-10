package easy.clicker.ctl.impl;

import com.sun.jna.platform.win32.WinDef.HWND;
import easy.clicker.ctl.ClientCtrl;
import easy.clicker.model.Point;
import easy.clicker.jna.User32Helper;
import easy.clicker.model.WindowLocation;
import easy.clicker.mod.Mod;
import easy.clicker.utils.OcrUtils;
import easy.clicker.model.ScreenShot;
import easy.clicker.utils.ClickUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunqipeng3
 * @date 2021-02-10 10:03:39
 */
public class Win10ClientCtrl implements ClientCtrl {
    private List<HWND> windowsCache = new ArrayList<>();

    @Override
    public int tryLocateWindows(List<String> clientNames) {
        for (String name : clientNames) {
            HWND window = User32Helper.findWindow(name);
            if (window == null) {
                findWindowFailLog(name);
                continue;
            }
            windowsCache.add(window);
        }
        return windowsCache.size();
    }

    @Override
    public void findAndClick(Mod mod) {
        for (HWND window : windowsCache) {
            User32Helper.focus(window);
            WindowLocation location = User32Helper.getLocation(window);

            if (location.getHeight() == 0 || location.getWidth() == 0){
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                location.setX(0);
                location.setY(0);
                location.setHeight(screenSize.height);
                location.setWidth(screenSize.width);
            }

            ScreenShot screenShot = new ScreenShot(location);
            OcrUtils.shotScreen(screenShot);

            Point point = OcrUtils.findPIP(screenShot, mod);
            if (point == null) {
                clickFailLog(mod, "定位失败");
                continue;
            }
            ClickUtils.click(point.getX(), point.getY());

            clickSuccessLog(mod, point);
        }
    }

}
