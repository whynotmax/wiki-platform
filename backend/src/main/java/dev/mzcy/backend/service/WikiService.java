package dev.mzcy.backend.service;

import dev.mzcy.backend.database.model.WikiEntry;
import dev.mzcy.backend.database.repository.WikiEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WikiService {
    private final WikiEntryRepository wikiRepository;

    public WikiService(WikiEntryRepository wikiRepository) {
        this.wikiRepository = wikiRepository;
    }

    @Cacheable("wikiEntries")
    public List<WikiEntry> findAll() {
        return wikiRepository.findAll();
    }

    @Cacheable(value = "wikiEntry", key = "#id")
    public Optional<WikiEntry> findById(String id) {
        return wikiRepository.findById(id);
    }

    @Caching(evict = {
        @CacheEvict(value = "wikiEntries", allEntries = true),
        @CacheEvict(value = "wikiEntry", key = "#entry.id"),
        @CacheEvict(value = "wikiSubEntries", allEntries = true),
        @CacheEvict(value = "wikiRootEntries", allEntries = true)
    })
    public WikiEntry save(WikiEntry entry) {
        return wikiRepository.save(entry);
    }

    @Caching(evict = {
        @CacheEvict(value = "wikiEntries", allEntries = true),
        @CacheEvict(value = "wikiEntry", key = "#id"),
        @CacheEvict(value = "wikiSubEntries", allEntries = true),
        @CacheEvict(value = "wikiRootEntries", allEntries = true)
    })
    public void deleteById(String id) {
        wikiRepository.deleteById(id);
    }

    @Cacheable(value = "wikiSubEntries", key = "#parentId")
    public List<WikiEntry> findSubEntries(String parentId) {
        return wikiRepository.findAll().stream()
                .filter(e -> e.isSubEntry() && parentId.equals(e.getParentId()))
                .collect(Collectors.toList());
    }

    @Cacheable("wikiRootEntries")
    public List<WikiEntry> findRootEntries() {
        return wikiRepository.findAll().stream()
                .filter(e -> !e.isSubEntry())
                .collect(Collectors.toList());
    }
}
