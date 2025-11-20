package io.github.pedrozaz.paymentcore;

import org.springframework.boot.SpringApplication;

public class TestSecurePaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
