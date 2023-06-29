package br.com.amarques.cookpedia.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RecipeForm(
        @NotBlank(message = "The [name] is required") String name,
        @NotBlank(message = "The [wayOfDoing] is required") String wayOfDoing,
        @NotEmpty(message = "At least one ingredient must be reported") List<String> ingredients) {

}
