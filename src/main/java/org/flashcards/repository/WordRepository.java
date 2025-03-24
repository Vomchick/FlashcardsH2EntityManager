package org.flashcards.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.flashcards.data.Language;
import org.flashcards.data.exceptions.WordNotFoundException;
import org.flashcards.model.Word;
import org.flashcards.repository.core.IRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class WordRepository implements IRepository {

    private final EntityManager entityManager;

    public WordRepository(EntityManager em) {
        this.entityManager = em;
    }

    @Transactional
    public void addWord(Word word) {
        entityManager.persist(word);
    }

    @Transactional
    public void deleteById(Long id) {
        getById(id).ifPresent(entityManager::remove);
    }

    @Transactional
    public void updateWord(Word word) {
        var updatedWord = getById(word.getId())
                .orElseThrow(WordNotFoundException::new);
        updatedWord.setEnglish(word.getEnglish());
        updatedWord.setGerman(word.getGerman());
        updatedWord.setPolish(word.getPolish());
    }

    public List<Word> getAll() {
        return entityManager.createQuery("from Word", Word.class).getResultList();
    }

    public Optional<Word> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Word.class, id));
    }

    public List<Word> getSortedByDesc(Language language) {
        return switch (language){
            case German -> entityManager.createQuery("from Word order by german desc ", Word.class).getResultList();
            case Polish -> entityManager.createQuery("from Word order by polish desc ", Word.class).getResultList();
            case English -> entityManager.createQuery("from Word order by english desc ", Word.class).getResultList();
        };
    }

    public List<Word> getSortedByAsc(Language language) {
        return switch (language){
            case German -> entityManager.createQuery("from Word order by german asc ", Word.class).getResultList();
            case Polish -> entityManager.createQuery("from Word order by polish asc ", Word.class).getResultList();
            case English -> entityManager.createQuery("from Word order by english asc ", Word.class).getResultList();
        };
    }

    public List<Word> search(String str) {
        String query = "select w from Word w where " +
                "lower(w.polish) like lower(:str) or " +
                "lower(w.english) like lower(:str) or " +
                "lower(w.german) like lower(:str)";

        return entityManager.createQuery(query, Word.class).setParameter("str", "%" + str + "%").getResultList();
    }
}
