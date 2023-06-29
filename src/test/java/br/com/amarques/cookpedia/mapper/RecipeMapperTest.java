package br.com.amarques.cookpedia.mapper;

import br.com.amarques.cookpedia.domain.Recipe;
import br.com.amarques.cookpedia.dto.form.RecipeForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class RecipeMapperTest {

    private final RecipeMapperImpl recipeMapper = new RecipeMapperImpl();

    @Test
    void should_convert_the_dto_into_a_domain_entity() {
        final var recipeForm = Instancio.create(RecipeForm.class);

        final var recipe = recipeMapper.toDomain(recipeForm);

        assertNull(recipe.getId());
        assertThat(recipe.getName()).isEqualTo(recipeForm.name());
        assertThat(recipe.getWayOfDoing()).isEqualTo(recipeForm.wayOfDoing());
        assertThat(recipe.getIngredients()).isEqualTo(recipeForm.ingredients());
    }

    @Test
    void should_convert_the_id_and_dto_into_a_domain_entity() {
        final var id = UUID.randomUUID().toString();
        final var recipeForm = Instancio.create(RecipeForm.class);

        final var recipe = recipeMapper.toDomain(id, recipeForm);

        assertThat(recipe.getId()).isEqualTo(id);
        assertThat(recipe.getName()).isEqualTo(recipeForm.name());
        assertThat(recipe.getWayOfDoing()).isEqualTo(recipeForm.wayOfDoing());
        assertThat(recipe.getIngredients()).isEqualTo(recipeForm.ingredients());
    }

    @Test
    void should_convert_the_domain_entity_into_a_dto() {
        final var recipe = Instancio.create(Recipe.class);

        final var recipeView = recipeMapper.toView(recipe);

        assertThat(recipeView.id()).isEqualTo(recipe.getId());
        assertThat(recipeView.name()).isEqualTo(recipe.getName());
        assertThat(recipeView.wayOfDoing()).isEqualTo(recipe.getWayOfDoing());
        assertThat(recipeView.ingredients()).isEqualTo(recipe.getIngredients());
    }
}
