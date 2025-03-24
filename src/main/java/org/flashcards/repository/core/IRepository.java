package org.flashcards.repository.core;

import org.flashcards.model.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRepository extends CrudRepository<Word, Long> {
    List<Word> findAllByOrderByPolishAsc();
    List<Word> findAllByOrderByEnglishAsc();
    List<Word> findAllByOrderByGermanAsc();
    List<Word> findAllByOrderByPolishDesc();
    List<Word> findAllByOrderByEnglishDesc();
    List<Word> findAllByOrderByGermanDesc();
    List<Word> findAllByEnglishContainingIgnoreCaseOrGermanContainingIgnoreCaseOrPolishContainingIgnoreCase(String str,String str2,String str3);
}
