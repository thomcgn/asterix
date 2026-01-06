package org.thomcgn.asterix.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CharacterRequest(
        @NotBlank String name,
        @Positive int age,
        @NotBlank String profession

) {
}
