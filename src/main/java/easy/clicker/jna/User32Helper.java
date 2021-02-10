package easy.clicker.jna;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;
import easy.clicker.model.WindowLocation;

public class User32Helper {
    static User32Ext USER32EXT = (User32Ext) Native.loadLibrary("user32", User32Ext.class, W32APIOptions.DEFAULT_OPTIONS);


    public static WinDef.HWND findWindow(String name){
        WinDef.HWND window = USER32EXT.FindWindow(null,name);
        return window;
    }


    public static WindowLocation getLocation(WinDef.HWND window){
        if (window==null){
            return null;
        }
        WinUser.WINDOWPLACEMENT place = new WinUser.WINDOWPLACEMENT();
        USER32EXT.GetWindowPlacement(window,place);
        WindowLocation location = new WindowLocation(place);
        return location;
    }

    public static void focus(WinDef.HWND window){
        USER32EXT.SwitchToThisWindow(window,true);
    }
}
