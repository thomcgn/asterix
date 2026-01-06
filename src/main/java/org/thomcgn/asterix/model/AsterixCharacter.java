package org.thomcgn.asterix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "asterix_chars")
@AllArgsConstructor
@NoArgsConstructor
public class AsterixCharacter {
    @Id
    private String id;
    private String name;
    private int age;
    private String profession;
}
