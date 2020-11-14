package edu.bsu.footbal.akinator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Starter {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Starter.class)
                .headless(false).run(args);
    }
}
