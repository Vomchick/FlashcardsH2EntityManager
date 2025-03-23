package org.flashcards.data;

public enum Order {
    Descending(1),
    Ascending(2);

    private final int value;

    private Order(int value) {
        this.value = value;
    }

    public static Order valueOfLabel(int label) {
        for (Order e : values()) {
            if (e.value == label) {
                return e;
            }
        }
        return null;
    }
}
