package org.flashcards.service;

import org.flashcards.data.Language;
import org.flashcards.data.Order;
import org.flashcards.model.Word;
import org.flashcards.repository.core.IRepository;
import org.flashcards.service.core.IWordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WordService implements IWordService {
    private IRepository repo;

    public WordService(IRepository repo) {
        this.repo = repo;
    }

    @Override
    public void addWord(Word word) {
        var words = getAll();

        if (words.isEmpty()) {
            word.setId(1L);
        }
        else{
            word.setId(words.getLast().getId()+1);
        }

        repo.save(word);
    }

    @Override
    public List<Word> getAll() {
        var words = new ArrayList<Word>();
        repo.findAll().forEach(words::add);
        return words;
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void updateWord(Word word) {
        repo.save(word);
    }

    @Override
    public Optional<Word> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<Word> getSortedBy(Language language, Order order) {
        List<Word> words = null;

        switch (language) {
            case English -> {
                switch (order) {
                    case Ascending -> words = repo.findAllByOrderByEnglishAsc();
                    case Descending -> words = repo.findAllByOrderByEnglishDesc();
                }
            }
            case Polish -> {
                switch (order) {
                    case Ascending -> words = repo.findAllByOrderByPolishAsc();
                    case Descending -> words = repo.findAllByOrderByPolishDesc();
                }
            }
            case German -> {
                switch (order) {
                    case Ascending -> words = repo.findAllByOrderByGermanAsc();
                    case Descending -> words = repo.findAllByOrderByGermanDesc();
                }
            }
        };

        return words;
    }

    @Override
    public void initialize() {
        if(!getAll().isEmpty()) {
            return;
        }

        addWord(new Word("kot", "cat", "katze"));
        addWord(new Word("pies", "dog", "hund"));
        addWord(new Word("dom", "house", "haus"));
        addWord(new Word("drzewo", "tree", "baum"));
        addWord(new Word("samochód", "car", "auto"));
    }

    @Override
    public List<Word> search(String str) {
        return repo.findAllByEnglishContainingIgnoreCaseOrGermanContainingIgnoreCaseOrPolishContainingIgnoreCase(str,str,str);
    }
}
