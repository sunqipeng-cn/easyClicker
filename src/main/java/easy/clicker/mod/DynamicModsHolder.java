package easy.clicker.mod;

import java.util.ArrayList;
import java.util.List;

public class DynamicModsHolder {
    private static final List<Mod> MOD_LIST = new ArrayList<>();

    public static void addMod(Mod mod){
        MOD_LIST.add(mod);
    }

    public static List<Mod> getMods(){
        return MOD_LIST;
    }
}
