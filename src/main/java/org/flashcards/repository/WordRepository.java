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

    public Optional<Word> getByLanguage(String word, Language language) {
        try {
            return switch (language) {
                case English ->
                        Optional.ofNullable(entityManager.createQuery("from Word where english =:word", Word.class)
                                .setParameter("word", word).getSingleResult());
                case Polish ->
                        Optional.ofNullable(entityManager.createQuery("from Word where polish =:word", Word.class)
                                .setParameter("word", word).getSingleResult());
                case German ->
                        Optional.ofNullable(entityManager.createQuery("from Word where german =:word", Word.class)
                                .setParameter("word", word).getSingleResult());
            };
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
