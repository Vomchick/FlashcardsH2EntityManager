package org.flashcards.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String polish;
    private String english;
    private String german;

    public Word() {}

    public String getGerman() {
        return german;
    }

    public void setGerman(String german) {
        this.german = german;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public Word(String polish, String english, String german) {
        this.polish = polish;
        this.english = english;
        this.german = german;
    }

    @Override
    public String toString() {
        return polish + "," + english + "," + german;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return polish.equalsIgnoreCase(word.polish) && english.equalsIgnoreCase(word.english) && german.equalsIgnoreCase(word.german);
    }

    @Override
    public int hashCode() {
        return Objects.hash(polish, english, german);
    }

    public Long getId() {
        return id;
    }
}
