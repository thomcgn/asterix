package org.thomcgn.asterix.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thomcgn.asterix.dto.CharacterRequest;
import org.thomcgn.asterix.dto.CharacterResponse;
import org.thomcgn.asterix.mapper.CharacterMapper;
import org.thomcgn.asterix.model.AsterixCharacter;
import org.thomcgn.asterix.repos.CharacterRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CharacterService {
    private final CharacterRepo repo;
    private final CharacterMapper mapper;

    public List<CharacterResponse> getAllCharacters(){
        return repo.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public Optional<CharacterResponse> getCharacterById(String id){
        return repo.findById(id).map(mapper::toResponse);
    }

    public Optional<CharacterResponse> getCharacterByName(String name){
        return repo.findByName(name).map(mapper::toResponse);
    }

    public CharacterResponse createCharacter(CharacterRequest dto){
        AsterixCharacter character = new AsterixCharacter();
        character.setName(dto.name());
        character.setAge(dto.age());
        character.setProfession(dto.profession());
        AsterixCharacter savedCharacter = repo.save(character);

        return mapper.toResponse(savedCharacter);
    }

    public AsterixCharacter updateCharacter(String id, CharacterRequest character){
        return repo.findById(id)
                .map(existing ->{
                    boolean nameChanged = !existing.getName().equals(character.name());
                    existing.setName(character.name());
                    existing.setAge(character.age());
                    existing.setProfession(character.profession());
                    return repo.save(existing);
                }).orElseThrow(()-> new RuntimeException("Charakter nicht gefunden!"));
    }

    public boolean deleteCharacter(String id){
        if(!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public List<CharacterResponse> createMultipleCharacters(List<CharacterRequest> dtos){
        return dtos.stream()
                .map(this::createCharacter)
                .toList();
    }
}
