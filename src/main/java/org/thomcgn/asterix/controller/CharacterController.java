package org.thomcgn.asterix.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thomcgn.asterix.dto.CharacterRequest;
import org.thomcgn.asterix.dto.CharacterResponse;
import org.thomcgn.asterix.model.AsterixCharacter;
import org.thomcgn.asterix.service.CharacterService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/asterix/characters")
public class CharacterController {

    private final CharacterService service;

    public CharacterController(CharacterService service){
        this.service = service;
    }

    @GetMapping
    public List<CharacterResponse> getAllCharacters(){
        return service.getAllCharacters();
    }

    @GetMapping("/{name}")
    public ResponseEntity<CharacterResponse> getCharacterByName(@PathVariable String name){
        return service.getCharacterByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CharacterResponse> createCharacter(@RequestBody CharacterRequest request){
        CharacterResponse savedCharacter = service.createCharacter(request);
        return ResponseEntity.status(201).body(savedCharacter);
    }

    @PostMapping("/batch")
    public List<CharacterResponse> createCharacters(@RequestBody List<CharacterRequest> characters){
        return  service.createMultipleCharacters(characters);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsterixCharacter> updateCharacter(@PathVariable String id, @RequestBody AsterixCharacter character){
        try {
            AsterixCharacter updated = service.updateCharacter(id, character);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AsterixCharacter> deleteCharacter(@PathVariable String id){
        boolean deleted = service.deleteCharacter(id);
        return  deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


}
