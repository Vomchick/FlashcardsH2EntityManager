package org.flashcards.repository.core;

import org.flashcards.model.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRepository extends CrudRepository<Word, Long> {
//    void addWord(Word word);
//    List<Word> getAll();
//    void deleteById(Long id);
//    void updateWord(Word word);
//    Optional<Word> getById(Long id);
//    List<Word> getSortedByDesc(Language language);
//    List<Word> getSortedByAsc(Language language);
//    Optional<Word> getByLanguage(String word, Language language);
    List<Word> findAllByOrderByPolishAsc();
    List<Word> findAllByOrderByEnglishAsc();
    List<Word> findAllByOrderByGermanAsc();
    List<Word> findAllByOrderByPolishDesc();
    List<Word> findAllByOrderByEnglishDesc();
    List<Word> findAllByOrderByGermanDesc();
    List<Word> findAllByEnglishContainingIgnoreCaseOrGermanContainingIgnoreCaseOrPolishContainingIgnoreCase(String str,String str2,String str3);
}
