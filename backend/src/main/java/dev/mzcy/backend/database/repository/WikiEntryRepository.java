package dev.mzcy.backend.database.repository;

import dev.mzcy.backend.database.model.WikiEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiEntryRepository extends MongoRepository<WikiEntry, String> {
}
