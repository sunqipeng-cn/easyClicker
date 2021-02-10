package easy.clicker.utils;

/**
 * @author sunqipeng3
 * @date 2021-02-10 11:09:54
 */
public class ThreadUtils {
    public static void sleep(long interval){
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
