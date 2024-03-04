package sample.spring.chapter09.bankapp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DatabaseConfig.class)
@ComponentScan("sample.spring.chapter09.bankapp.service")
public class SpringConfig {
}
