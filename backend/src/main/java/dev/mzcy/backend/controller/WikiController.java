package dev.mzcy.backend.controller;

import dev.mzcy.backend.database.model.WikiEntry;
import dev.mzcy.backend.service.WikiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wiki") // wie von dir gewünscht ohne /api
public class WikiController {

    private final WikiService wikiService;
    private final Random random = new Random();

    public WikiController(WikiService wikiService) {
        this.wikiService = wikiService;
    }

    // GET /wiki - all entries
    @GetMapping
    public List<WikiEntry> getAllEntries() {
        return wikiService.findAll();
    }

    // GET /wiki/{id} - get single entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<WikiEntry> getEntryById(@PathVariable String id) {
        return wikiService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /wiki/random - random entry
    @GetMapping("/random")
    public ResponseEntity<WikiEntry> getRandomEntry() {
        List<WikiEntry> allEntries = wikiService.findAll();
        if (allEntries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        WikiEntry randomEntry = allEntries.get(random.nextInt(allEntries.size()));
        return ResponseEntity.ok(randomEntry);
    }

    // GET /wiki/search?query=... - search entries by title
    @GetMapping("/search")
    public List<WikiEntry> searchEntries(@RequestParam String query) {
        String lowerQuery = query.toLowerCase();
        return wikiService.findAll().stream()
                .filter(entry -> entry.getTitle() != null && entry.getTitle().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    // POST /wiki - Create new entry
    @PostMapping
    public WikiEntry createEntry(@RequestBody WikiEntry entry) {
        return wikiService.save(entry);
    }

    // PUT /wiki/{id} - update existing entry
    @PutMapping("/{id}")
    public ResponseEntity<WikiEntry> updateEntry(@PathVariable String id, @RequestBody WikiEntry updatedEntry) {
        return wikiService.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedEntry.getTitle());
                    existing.setContent(updatedEntry.getContent());
                    return ResponseEntity.ok(wikiService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /wiki/{id} - delete entry by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable String id) {
        if (wikiService.findById(id).isPresent()) {
            wikiService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}