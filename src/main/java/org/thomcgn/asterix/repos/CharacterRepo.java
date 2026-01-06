package org.thomcgn.asterix.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.thomcgn.asterix.model.AsterixCharacter;

import java.util.Optional;

@Repository
public interface CharacterRepo extends MongoRepository<AsterixCharacter, String> {
    Optional<AsterixCharacter> findByName(String name);
}
