package org.flashcards.service.core;

import org.flashcards.data.Language;
import org.flashcards.data.Order;
import org.flashcards.model.Word;

import java.util.List;
import java.util.Optional;

public interface IFileService {
    void addWord(Word word);
    List<Word> getAll();
    void deleteById(Long id);
    void updateWord(Word word);
    Optional<Word> getById(Long id);
    List<Word> getSortedBy(Language language, Order order);
    void initialize();
    Optional<Word> getByLanguage(String word, Language language);
}
