package com.ebook;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.LogManager;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
        SpringApplication.run(Main.class,args);

    }
}




