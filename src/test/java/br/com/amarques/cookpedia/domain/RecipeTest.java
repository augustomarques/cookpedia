package br.com.amarques.cookpedia.domain;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecipeTest {

    private Integer numberOfIngredients;

    @BeforeEach
    public void before() {
        this.numberOfIngredients = ThreadLocalRandom.current().nextInt(0, 100);
    }

    @Test
    void should_create_recipe() {
        final String id = UUID.randomUUID().toString();
        final String name = Instancio.create(String.class);
        final String wayOfDoing = Instancio.create(String.class);
        final List<String> ingredients = Instancio.ofList(String.class).size(numberOfIngredients).create();

        final var recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(name);
        recipe.setWayOfDoing(wayOfDoing);
        recipe.setIngredients(ingredients);

        assertNotNull(recipe);
        assertThat(recipe.getId()).isEqualTo(id);
        assertThat(recipe.getName()).isEqualTo(name);
        assertThat(recipe.getWayOfDoing()).isEqualTo(wayOfDoing);
        assertThat(recipe.getIngredients()).isEqualTo(ingredients);
    }
}
