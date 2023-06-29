package br.com.amarques.cookpedia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@EqualsAndHashCode
public class Recipe {

    @Id
    private String id;

    @Indexed
    private String name;

    private String wayOfDoing;

    @Indexed
    private List<String> ingredients;

}
