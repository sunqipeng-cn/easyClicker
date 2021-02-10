package easy.clicker.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import easy.clicker.mod.DynamicModsHolder;
import easy.clicker.mod.Mod;
import easy.clicker.utils.OcrUtils;

import java.io.File;
import java.util.Properties;

/**
 * @author sunqipeng3
 * @date 2021-02-10 09:53:49
 */
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private static final String ENCODE = "utf-8";
    private static Properties sysConfig;

    static {
        initConfig();
    }

    private static void initConfig() {
        try {
//            E:/idea/yystools/src/main/resources/application.properties
//            E:/idea/yystools/src/main/resources/mods
            String confPath = System.getProperty("conf");
            String modsPath = System.getProperty("mods");

            if (confPath == null || confPath.length() == 0) {
                logger.error("配置文件异常，系统退出");
                System.exit(0);
            }
            sysConfig = PropertiesLoaderUtils.loadProperties(
                    new EncodedResource(new PathResource(confPath), ENCODE));
            logger.info("读取到配置文件: " + confPath);


            File modsDir = new File(modsPath);
            File[] files = modsDir.listFiles();
            if (files == null) {
                logger.error("配置文件异常，系统退出");
                System.exit(0);
            }
            for (File modFile : files) {
                Mod mod = OcrUtils.dynamicMod(modFile);
                DynamicModsHolder.addMod(mod);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("配置文件读取失败", e);
        }
    }

    public static String getSysConf(String key) {
        return sysConfig.getProperty(key);
    }
    public static String getSysConf(String key, String defaultValue) {
        return sysConfig.getProperty(key, defaultValue);
    }
}
