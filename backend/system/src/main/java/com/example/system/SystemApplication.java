package com.example.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@ComponentScan(basePackages  = {"com.example.system", "com.example.common"})
@EnableTransactionManagement  // Open Database Transaction.
//@EnableFeignClients          // Enable OpenFeign
//@EnableDiscoveryClient      // Enable Service register and discovery.
@SpringBootApplication
@EnableAsync     // Enable asynchronous method.
public class SystemApplication {

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
        SpringApplication.run(SystemApplication.class, args);
        log.info(BANNER);
    }
}
