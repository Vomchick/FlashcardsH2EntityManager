package org.flashcards.service;

import org.flashcards.data.Language;
import org.flashcards.data.Order;
import org.flashcards.model.Word;
import org.flashcards.repository.core.IRepository;
import org.flashcards.service.core.IFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
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
        repo.update
    }

    @Override
    public Optional<Word> getById(Long id) {
        return repo.findById(id);
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
        if(!repo.findAll().iterator().hasNext()) {
            return;
        }

        repo.save(new Word("kot", "cat", "katze"));
        repo.save(new Word("pies", "dog", "hund"));
        repo.save(new Word("dom", "house", "haus"));
        repo.save(new Word("drzewo", "tree", "baum"));
        repo.save(new Word("samochód", "car", "auto"));
    }

    @Override
    public Optional<Word> getByLanguage(String word, Language language) {
        return repo.getByLanguage(word, language);
    }
}
