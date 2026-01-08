package org.thomcgn.asterix.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thomcgn.asterix.dto.CharacterRequest;
import org.thomcgn.asterix.dto.CharacterResponse;
import org.thomcgn.asterix.mapper.CharacterMapper;
import org.thomcgn.asterix.model.AsterixCharacter;
import org.thomcgn.asterix.repos.CharacterRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    @Mock
    private CharacterRepo characterRepo;
    @Mock
    private CharacterMapper mapper;

    @InjectMocks
    private CharacterService service;

    @Test
    void getAllCharacters() {
        //GIVEN
        AsterixCharacter character = new AsterixCharacter("1","Name",100,"Smith");
        List<AsterixCharacter> characters = new ArrayList<>();
        characters.add(character);
        Mockito.when(characterRepo.findAll()).thenReturn(characters);
        //WHEN
        List<CharacterResponse> responseList = service.getAllCharacters();
        //THEN
        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        verify(characterRepo).findAll();
    }

    @Test
    void getCharacterById() {
        //GIVEN
        AsterixCharacter character = new AsterixCharacter("1","Name",100,"Smith");
        CharacterResponse characterResponse = new CharacterResponse("1","Name",100,"Smith");
        Mockito.when(characterRepo.findById("1"))
                .thenReturn(Optional.of(character));

        Mockito.when(mapper.toResponse(character))
                .thenReturn(characterResponse);

        // WHEN
        Optional<CharacterResponse> response =
                service.getCharacterById("1");

        // THEN
        assertTrue(response.isPresent());
        assertEquals("1", response.get().id());

        verify(characterRepo).findById("1");
        verify(mapper).toResponse(character);
    }

    @Test
    void updateCharacter() {
        // GIVEN
        AsterixCharacter existing =
                new AsterixCharacter("1", "OldName", 50, "Farmer");

        CharacterRequest request =
                new CharacterRequest("NewName", 100, "Warrior");

        AsterixCharacter updated =
                new AsterixCharacter("1", "NewName", 100, "Warrior");

        CharacterResponse responseDto =
                new CharacterResponse("1", "NewName", 100, "Warrior");

        Mockito.when(characterRepo.findById("1"))
                .thenReturn(Optional.of(existing));

        Mockito.when(characterRepo.save(any(AsterixCharacter.class)))
                .thenReturn(updated);

        Mockito.when(mapper.toResponse(updated))
                .thenReturn(responseDto);

        // WHEN
        CharacterResponse response = service.updateCharacter("1", request);

        // THEN
        assertNotNull(response);
        assertEquals("NewName", response.name());
        assertEquals(100, response.age());
        assertEquals("Warrior", response.profession());

        verify(characterRepo).findById("1");
        verify(characterRepo).save(existing);
        verify(mapper).toResponse(updated);
    }

    @Test
    void deleteCharacter() {
        // GIVEN
        Mockito.when(characterRepo.existsById("1")).thenReturn(true);

        // WHEN
        service.deleteCharacter("1");

        // THEN
        verify(characterRepo).existsById("1");
        verify(characterRepo).deleteById("1");
    }
}