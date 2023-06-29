package br.com.amarques.cookpedia.repositoty;

import br.com.amarques.cookpedia.domain.Recipe;
import br.com.amarques.cookpedia.repository.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@DataMongoTest()
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
@Import(RecipePopulatorConfiguration.class)
class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void test() {
        List<Recipe> recipes = recipeRepository.findAll();
        for(Recipe recipe : recipes) {
            System.out.println("RECEITA >>>>> " + recipe);
        }
    }

    @Test
    void should() {
        List<Recipe> recipes = recipeRepository.findByIngredients(List.of("Rice"));

        assertThat(recipes, hasSize(3));
    }

    @AfterEach
    void cleanUpDatabase() {
        recipeRepository.deleteAll();
    }
}
