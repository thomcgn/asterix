package org.thomcgn.asterix.mapper;

import org.springframework.stereotype.Component;
import org.thomcgn.asterix.dto.CharacterResponse;
import org.thomcgn.asterix.model.AsterixCharacter;

@Component
public class CharacterMapper {

    public CharacterResponse toResponse(AsterixCharacter asterixCharacter){

        return new CharacterResponse(
                asterixCharacter.getId(),
                asterixCharacter.getName(),
                asterixCharacter.getAge(),
                asterixCharacter.getProfession()
        );
    }
}
