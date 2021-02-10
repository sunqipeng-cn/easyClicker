package easy.clicker.model;

import com.sun.jna.platform.win32.WinUser;

public class WindowLocation {
    private int height;
    private int width;
    private int x;
    private int y;

    public WindowLocation() {
    }

    public WindowLocation(int height, int width, int x, int y) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public WindowLocation(WinUser.WINDOWPLACEMENT place) {
        this.height = place.rcNormalPosition.toRectangle().height;
        this.width = place.rcNormalPosition.toRectangle().width;
        this.x =  place.rcNormalPosition.left;
        this.y = place.rcNormalPosition.top;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "WindowLocation{" +
                "height=" + height +
                ", width=" + width +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
