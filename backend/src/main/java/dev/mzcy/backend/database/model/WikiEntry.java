package dev.mzcy.backend.database.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wiki_entries")
@Data
public class WikiEntry {

    @Id
    private String id;  // Wiki entry ID

    private String title; // Title of the wiki entry
    private String content; // Markdown
}