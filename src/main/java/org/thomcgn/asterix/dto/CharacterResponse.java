package org.thomcgn.asterix.dto;

public record CharacterResponse(
        String id,
        String name,
        int age,
        String profession
) {
}
