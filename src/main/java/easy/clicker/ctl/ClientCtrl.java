package easy.clicker.ctl;

import easy.clicker.model.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import easy.clicker.mod.Mod;

import java.util.List;

/**
 * @author sunqipeng3
 * @date 2021-02-10 10:03:15
 */
public interface ClientCtrl {
    Logger LOGGER = LoggerFactory.getLogger(ClientCtrl.class);

    int tryLocateWindows(List<String> clientNames);

    void findAndClick(Mod mod);

    default void findWindowFailLog(String windowName){
        LOGGER.info("窗口未找到- {}", windowName);
    }
    default void clickSuccessLog(Mod mod, Point point){
        LOGGER.info("do {} at {} success", mod.getModName(), point.toString());
    }
    default void clickFailLog(Mod mod, String text){
        LOGGER.info("do {} fail, reason: {}", mod.getModName(), text);
    }
}
