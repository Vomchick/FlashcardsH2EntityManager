package org.flashcards.controller;

import org.flashcards.controller.core.IController;
import org.flashcards.data.Language;
import org.flashcards.data.Order;
import org.flashcards.model.Word;
import org.flashcards.service.core.IWordService;
import org.flashcards.service.core.ITextFormatter;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Controller
public class FlashcardsController implements IController {
    private final IWordService wordService;
    private final ITextFormatter textFormatter;
    private final Scanner scanner;

    public FlashcardsController(IWordService wordService, ITextFormatter textFormatter, Scanner scanner) {
        this.wordService = wordService;
        this.textFormatter = textFormatter;
        this.scanner = scanner;
    }

    public void addWord() {
        System.out.print("\nEnter the word (Polish, English, German separated by commas): ");
        String[] parts = scanner.nextLine().split(",");

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        if (parts.length == 3) {
            Word newWord = new Word(parts[0], parts[1], parts[2]);

            for (Word word : wordService.getAll()) {
                if (word.equals(newWord)) {
                    System.out.println("\nThe word is already in the dictionary.");
                    return;
                }
            }
            wordService.addWord(newWord);
            System.out.println("\nWord added!");
        } else {
            System.err.println("\nIncorrect input format.");
        }
    }

    public void displayAllWords() {
        List<Word> entries = wordService.getAll();
        displayWords(entries);
    }

    @Override
    public void displaySortedWords() {
        var language = getLanguage();

        var order = -1;
        System.out.println("What order?");
        while(order != 1 && order != 2) {
            System.out.println("1. Descending");
            System.out.println("2. Ascending");
            order = scanner.nextInt();
        }

        List<Word> words = wordService.getSortedBy(Language.valueOfLabel(language), Order.valueOfLabel(order));
        displayWords(words);
    }

    @Override
    public void searchWord() {
        System.out.println("\nEnter word");
        String searchedWord = scanner.next();
        scanner.nextLine();

        var retrievedWords = wordService.search(searchedWord);
        if (retrievedWords.isEmpty()) {
            System.out.println("Word not found!");
        }
        else {
            displayWords(retrievedWords);
        }
    }

    @Override
    public void updateWord() {
        var searchedWord = getOneWord();

        System.out.print("\nEnter updated word (Polish, English, German separated by commas): ");
        String[] parts = scanner.nextLine().split(",");

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        if (parts.length == 3) {
            searchedWord.setPolish(parts[0]);
            searchedWord.setEnglish(parts[1]);
            searchedWord.setGerman(parts[2]);

            for (Word word : wordService.getAll()) {
                if (word.equals(searchedWord)) {
                    System.out.println("\nThe word is already in the dictionary.");
                    return;
                }
            }
            wordService.updateWord(searchedWord);
        }
        else {
            System.out.println("\nWord not found.");
        }

    }

    @Override
    public void deleteWord() {
        var searchedWord = getOneWord();

        wordService.deleteById(searchedWord.getId());
        System.out.println("\nWord deleted!");
    }

    private int getLanguage() {
        var language = -1;
        System.out.println("\nWhich language?");
        while(language != 1 && language != 2 && language != 3) {
            System.out.println("1. Polish");
            System.out.println("2. English");
            System.out.println("3. German");
            language = scanner.nextInt();
        }
        return language;
    }

    public void startTest() {
        List<Word> entries = wordService.getAll();
        if (entries.isEmpty()) {
            System.out.println("\nThere are no words for the test.");
            return;
        }
        Random random = new Random();
        Word word = entries.get(random.nextInt(entries.size()));

        int randomLanguage = random.nextInt(3);

        switch (randomLanguage) {
            case 0 -> { //Polish
                System.out.println("\nTranslate the word from Polish: " + word.getPolish());
                System.out.print("English: ");
                String userEnglish = scanner.nextLine().trim().toLowerCase();
                System.out.print("German: ");
                String userGerman = scanner.nextLine().trim().toLowerCase();

                if (userEnglish.equals(word.getEnglish().toLowerCase()) &&
                        userGerman.equals(word.getGerman().toLowerCase())) {
                    System.out.println("\nCorrect!");
                } else {
                    System.out.println("Wrong! Correct answer: English = " + word.getEnglish() + ", German = " + word.getGerman());
                }
            }
            case 1 -> { //English
                System.out.println("\nTranslate the word from English: " + word.getEnglish());
                System.out.print("Polish: ");
                String userPolish = scanner.nextLine().trim().toLowerCase();
                System.out.print("German: ");
                String userGerman = scanner.nextLine().trim().toLowerCase();

                if (userPolish.equals(word.getPolish().toLowerCase()) &&
                        userGerman.equals(word.getGerman().toLowerCase())) {
                    System.out.println("\nCorrect!");
                } else {
                    System.out.println("Wrong! Correct answer: Polish = " + word.getPolish() + ", German = " + word.getGerman());
                }
            }
            case 2 -> { //German
                System.out.println("Translate the word from German: " + word.getGerman());
                System.out.print("Polish: ");
                String userPolish = scanner.nextLine().trim().toLowerCase();
                System.out.print("English: ");
                String userEnglish = scanner.nextLine().trim().toLowerCase();

                if (userPolish.equals(word.getPolish().toLowerCase()) &&
                        userEnglish.equals(word.getEnglish().toLowerCase())) {
                    System.out.println("\nCorrect!");
                } else {
                    System.out.println("Wrong! Correct answer: Polish = " + word.getPolish() + ", English = " + word.getEnglish());
                }
            }
            default -> throw new IllegalStateException();
        }
    }

    private Word getOneWord(){
        List<Word> retrievedWords = null;

        do {
            System.out.println("\nEnter searched word");
            String searchedWord = scanner.next();
            scanner.nextLine();
            retrievedWords = wordService.search(searchedWord);
            displayWords(retrievedWords);
            if(retrievedWords.size() != 1) {
                System.out.println("Too many words found, adjust your request");
            }

        } while (retrievedWords.size() != 1);

        return retrievedWords.getFirst();
    }

    private void displayWords(List<Word> words) {
        if (words == null || words.isEmpty()) {
            System.out.println("\nThe dictionary is empty.");
            return;
        }

        String format = "%-20s | %-20s | %-20s%n";

        System.out.println();
        System.out.printf(format, "Polish", "English", "German");
        System.out.println("----------------------------------------------------------");

        for (Word word : words) {
            System.out.printf(format, textFormatter.format(word.getPolish()), textFormatter.format(word.getEnglish()), textFormatter.format(word.getGerman()));
        }
    }
}
