package org.flashcards;

import org.flashcards.controller.FlashcardsController;
import org.flashcards.service.FileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class FlashcardsApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);
        FlashcardsController controller = context.getBean(FlashcardsController.class);
        FileService fileService = context.getBean(FileService.class);
        Scanner scanner = context.getBean(Scanner.class);

        fileService.initialize();

        while (true) {
            System.out.println("\n1. Show all words");
            System.out.println("2. Show all sorted by...");
            System.out.println("3. Add a word");
            System.out.println("4. Start the test");
            System.out.println("5. Exit");
            System.out.print("\nChoose an action: ");
            String choice = scanner.next();
            scanner.nextLine();

            switch (choice) {
                case "1" -> controller.displayWords();
                case "2" -> controller.displaySortedWords();
                case "3" -> controller.addWord();
                case "4" -> controller.startTest();
                case "5" -> System.exit(0);
                default -> System.out.println("\nNot a valid choice!");
            }
        }
    }

}
