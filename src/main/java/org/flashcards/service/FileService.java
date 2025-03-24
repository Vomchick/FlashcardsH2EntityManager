package org.flashcards.service;

import org.flashcards.data.Language;
import org.flashcards.data.Order;
import org.flashcards.model.Word;
import org.flashcards.repository.core.IRepository;
import org.flashcards.service.core.IFileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileService implements IFileService {
    private IRepository repo;

    public FileService(IRepository repo) {
        this.repo = repo;
    }

    @Override
    public void addWord(Word word) {
        repo.addWord(word);
    }

    @Override
    public List<Word> getAll() {
        return repo.getAll();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void updateWord(Word word) {
        repo.updateWord(word);
    }

    @Override
    public Optional<Word> getById(Long id) {
        return repo.getById(id);
    }

    @Override
    public List<Word> getSortedBy(Language language, Order order) {
        return switch (order){
            case Ascending -> repo.getSortedByAsc(language);
            case Descending -> repo.getSortedByDesc(language);
        };
    }

    @Override
    public void initialize() {
        if(!repo.getAll().isEmpty()) {
            return;
        }

        repo.addWord(new Word("kot", "cat", "katze"));
        repo.addWord(new Word("pies", "dog", "hund"));
        repo.addWord(new Word("dom", "house", "haus"));
        repo.addWord(new Word("drzewo", "tree", "baum"));
        repo.addWord(new Word("samochód", "car", "auto"));
    }

    @Override
    public List<Word> search(String str) {
        return repo.search(str);
    }
}
