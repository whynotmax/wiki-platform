package dev.mzcy.backend.service;

import dev.mzcy.backend.database.model.WikiEntry;
import dev.mzcy.backend.database.repository.WikiEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WikiService {
    private final WikiEntryRepository wikiRepository;

    public WikiService(WikiEntryRepository wikiRepository) {
        this.wikiRepository = wikiRepository;
    }

    public List<WikiEntry> findAll() {
        return wikiRepository.findAll();
    }

    public Optional<WikiEntry> findById(String id) {
        return wikiRepository.findById(id);
    }

    public WikiEntry save(WikiEntry entry) {
        return wikiRepository.save(entry);
    }

    public void deleteById(String id) {
        wikiRepository.deleteById(id);
    }
}