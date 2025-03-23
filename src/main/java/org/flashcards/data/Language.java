package org.flashcards.data;

public enum Language {
    Polish(1),
    English(2),
    German(3);

    private final int value;

    private Language(int value) {
        this.value = value;
    }

    public static Language valueOfLabel(int label) {
        for (Language e : values()) {
            if (e.value == label) {
                return e;
            }
        }
        return null;
    }
}
