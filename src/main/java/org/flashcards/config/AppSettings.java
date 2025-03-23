package org.flashcards.config;

import org.flashcards.service.core.ITextFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Scanner;

@Configuration
public class AppSettings {

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }

    @Bean
    @Profile("default")
    public ITextFormatter defaultTextFormatter() {
        return text -> text;
    }

    @Bean
    @Profile("uppercase")
    public ITextFormatter uppercaseTextFormatter() {
        return String::toUpperCase;
    }

    @Bean
    @Profile("lowercase")
    public ITextFormatter lowercaseTextFormatter() {
        return String::toLowerCase;
    }
}
