package easy.clicker;

import easy.clicker.ctl.ClientStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.setProperty("java.awt.headless", "false");

        ClientStarter clientStarter = new ClientStarter();
        clientStarter.start();
    }
}
