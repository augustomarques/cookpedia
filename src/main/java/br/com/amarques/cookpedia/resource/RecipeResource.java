package br.com.amarques.cookpedia.resource;

import br.com.amarques.cookpedia.dto.form.RecipeForm;
import br.com.amarques.cookpedia.dto.view.RecipeView;
import br.com.amarques.cookpedia.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Recipes")
@RestController
@RequestMapping("/api/recipes")
public class RecipeResource {

    private final RecipeService recipeService;

    @PostMapping
    @Operation(summary = "Register a new recipe")
    public ResponseEntity<RecipeView> create(@Valid @RequestBody final RecipeForm recipeForm,
                                             final UriComponentsBuilder uriBuilder) {
        log.info("REST request to create a new Recipe {}", recipeForm.name());

        final var recipeView = recipeService.create(recipeForm);
        final var uri = uriBuilder.path("/api/recipes/{id}").build(recipeView.id());

        return ResponseEntity.created(uri).body(recipeView);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search a Recipe by ID")
    public ResponseEntity<RecipeView> get(@PathVariable final String id) {
        log.info("REST request to get an Recipe [id: {}]", id);

        final var recipeDTO = recipeService.getById(id);

        return ResponseEntity.ok(recipeDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Change a registered Recipe")
    public ResponseEntity<Void> update(@PathVariable final String id,
                                       @Valid @RequestBody final RecipeForm recipeDTO) {
        log.info("REST request to update an Recipe [id: {}] [dto: {}]", id, recipeDTO);

        recipeService.update(id, recipeDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Search all recipes")
    public ResponseEntity<List<RecipeView>> gelAll(@RequestParam(value = "page", defaultValue = "0", required = false) final int page,
                                                   @RequestParam(value = "size", defaultValue = "10", required = false) final int size) {
        log.info("REST request to gel all Recipes [Page: {}  - Size: {}]", page, size);

        final var recipes = recipeService.getAll(PageRequest.of(page, size));

        return ResponseEntity.ok().body(recipes);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove a Recipe")
    public ResponseEntity<Void> delete(@PathVariable final String id) {
        log.info("REST request to delete an Recipe [id: {}] and all Ingredients", id);

        recipeService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search for recipes that have one of the ingredients listed")
    public ResponseEntity<List<RecipeView>> findByIngredients(@RequestParam final List<String> ingredients) {
        log.info("REST request to get Recipes from Ingredients [{}]", ingredients);

        final var recipes = recipeService.getByIngredients(ingredients);

        return ResponseEntity.ok().body(recipes);
    }
}
