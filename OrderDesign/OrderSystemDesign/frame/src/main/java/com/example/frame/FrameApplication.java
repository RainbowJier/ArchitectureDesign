package com.example.frame;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FrameApplication {

    public static final String BANNER = "\n" +
            "   _____   _    _    _____    _____   ______    _____    _____ \n" +
            "  / ____| | |  | |  / ____|  / ____| |  ____|  / ____|  / ____|\n" +
            " | (___   | |  | | | |      | |      | |__    | (___   | (___  \n" +
            "  \\___ \\  | |  | | | |      | |      |  __|    \\___ \\   \\___ \\ \n" +
            "  ____) | | |__| | | |____  | |____  | |____   ____) |  ____) |\n" +
            " |_____/   \\____/   \\_____|  \\_____| |______| |_____/  |_____/ \n" +
            "                                                               \n" +
            "                                                               \n";

    public static void main(String[] args) {
        SpringApplication.run(FrameApplication.class, args);
        log.info(BANNER);
    }

}
