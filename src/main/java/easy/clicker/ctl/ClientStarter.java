package easy.clicker.ctl;

import easy.clicker.conf.AppCogfigPropKey;
import easy.clicker.conf.AppConfig;
import easy.clicker.conf.OsName;
import easy.clicker.ctl.impl.Win10ClientCtrl;
import easy.clicker.mod.DynamicModsHolder;
import easy.clicker.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author sunqipeng3
 * @date 2021-02-10 10:50:03
 */
public class ClientStarter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientStarter.class);

    public void start(){
        String osName = System.getProperty("os.name").toLowerCase().trim();
        ClientCtrl clientCtrl = getClientForOs(osName);
        if (clientCtrl == null){
            LOGGER.info("暂不支持当前系统 {}", osName);
            System.exit(0);
        }

        String clients = AppConfig.getSysConf(AppCogfigPropKey.CLIENTS);
        String intervalStr = AppConfig.getSysConf(AppCogfigPropKey.INTERVAL, "100");

        List<String> clientList = Arrays.asList(clients.split(","));
        clientCtrl.tryLocateWindows(clientList);
        long interval = Long.parseLong(intervalStr);

        for (;;){
            DynamicModsHolder.getMods().forEach(clientCtrl::findAndClick);
            ThreadUtils.sleep(interval);
        }
    }

    private ClientCtrl getClientForOs(String osName){
        if (OsName.WIN10.equals(osName)) {
            return new Win10ClientCtrl();
        }
        return null;
    }
}
