package br.com.amarques.cookpedia.resource;

import br.com.amarques.cookpedia.dto.form.RecipeForm;
import br.com.amarques.cookpedia.dto.view.RecipeView;
import br.com.amarques.cookpedia.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RecipeResource.class)
class RecipeResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    @Test
    void should_return_all_recipes_registered() throws Exception {
        final var recipes = Instancio.ofList(RecipeView.class).size(10).create();

        when(recipeService.getAll(PageRequest.of(0, 10))).thenReturn(recipes);

        final var mvcResult = mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andReturn();

        final List<RecipeView> recipesView = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, RecipeView.class));

        assertThat(recipesView).isNotEmpty().hasSize(10);
    }

    @Test
    void should_return_recipe_when_find_by_id() throws Exception {
        final var id = UUID.randomUUID().toString();
        final var recipeView = Instancio.of(RecipeView.class)
                .set(field("id"), id)
                .create();

        when(recipeService.getById(id)).thenReturn(recipeView);

        final var mvcResult = mockMvc.perform(get("/api/recipes/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        final var recipe = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), RecipeView.class);

        assertThat(recipe).isNotNull();
        assertThat(recipe.id()).isEqualTo(id);
        assertThat(recipe.name()).isEqualTo(recipeView.name());
        assertThat(recipe.wayOfDoing()).isEqualTo(recipeView.wayOfDoing());
        assertThat(recipe.ingredients()).isNotEmpty().hasSameElementsAs(recipeView.ingredients());
    }

    @Test
    void should_create() throws Exception {
        final var id = UUID.randomUUID().toString();
        final var recipeForm = Instancio.create(RecipeForm.class);
        final var recipeView = new RecipeView(id, recipeForm.name(), recipeForm.wayOfDoing(), recipeForm.ingredients());

        when(recipeService.create(recipeForm)).thenReturn(recipeView);

        final var mvcResult = mockMvc.perform(post("/api/recipes")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(recipeForm)))
                .andExpect(status().isCreated())
                .andReturn();

        final var registeredRecipe = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                RecipeView.class);

        assertThat(registeredRecipe).isNotNull();
        assertThat(registeredRecipe.id()).isEqualTo(id);
        assertThat(registeredRecipe.name()).isEqualTo(recipeForm.name());
        assertThat(registeredRecipe.wayOfDoing()).isEqualTo(recipeForm.wayOfDoing());
        assertThat(registeredRecipe.ingredients()).isNotEmpty().hasSameElementsAs(recipeForm.ingredients());
    }

    @Test
    void should_update() throws Exception {
        final var id = UUID.randomUUID().toString();
        final var recipeForm = Instancio.create(RecipeForm.class);

        mockMvc.perform(put("/api/recipes/{id}", id)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(recipeForm)))
                .andExpect(status().isOk());

        verify(recipeService).update(id, recipeForm);
    }

    @Test
    void should_find_by_ingredients() throws Exception {
        final var ingredients = Instancio.ofList(String.class).size(2).create();
        final var registeredRecipesView = Instancio.ofList(RecipeView.class).size(5).create();

        when(recipeService.getByIngredients(ingredients)).thenReturn(registeredRecipesView);

        final var mvcResult = mockMvc.perform(get("/api/recipes/search")
                        .param("ingredients", ingredients.get(0))
                        .param("ingredients", ingredients.get(1)))
                .andExpect(status().isOk())
                .andReturn();

        final List<RecipeView> recipes = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, RecipeView.class));

        assertThat(recipes).isNotEmpty().hasSize(registeredRecipesView.size());
    }

    @Test
    void should_delete() throws Exception {
        final var id = UUID.randomUUID().toString();

        mockMvc.perform(delete("/api/recipes/{recipeId}", id)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(recipeService).delete(id);
    }
}
