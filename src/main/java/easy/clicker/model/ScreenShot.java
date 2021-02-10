package easy.clicker.model;

public class ScreenShot extends Img {
    private int x;
    private int y;

    public ScreenShot() {
    }

    public ScreenShot(WindowLocation windowLocation) {
        int scrHeight = windowLocation.getHeight();
        int scrWidth = windowLocation.getWidth();
        int scrX = windowLocation.getX();
        int scrY = windowLocation.getY();

        setHeight(scrHeight);
        setWidth(scrWidth);
        setX(scrX);
        setY(scrY);
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
}
