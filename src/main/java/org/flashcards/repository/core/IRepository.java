package org.flashcards.repository.core;

import org.flashcards.data.Language;
import org.flashcards.model.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IRepository extends CrudRepository<Word, Long> {
//    void addWord(Word word);
//    List<Word> getAll();
//    void deleteById(Long id);
//    void updateWord(Word word);
//    Optional<Word> getById(Long id);
//    List<Word> getSortedByDesc(Language language);
//    List<Word> getSortedByAsc(Language language);
//    Optional<Word> getByLanguage(String word, Language language);
}
