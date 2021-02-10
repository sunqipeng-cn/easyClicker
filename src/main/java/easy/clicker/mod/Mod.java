package easy.clicker.mod;

import easy.clicker.model.Img;

public class Mod extends Img {
    private String modName;

    public Mod(String modName) {
        this.modName = modName;
    }


    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }
}
