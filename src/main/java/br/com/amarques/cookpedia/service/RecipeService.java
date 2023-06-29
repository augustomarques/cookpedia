package br.com.amarques.cookpedia.service;

import br.com.amarques.cookpedia.domain.Recipe;
import br.com.amarques.cookpedia.dto.form.RecipeForm;
import br.com.amarques.cookpedia.dto.view.RecipeView;
import br.com.amarques.cookpedia.exception.NotFoundException;
import br.com.amarques.cookpedia.mapper.RecipeMapper;
import br.com.amarques.cookpedia.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public RecipeView create(final RecipeForm recipeForm) {
        final var recipe = recipeMapper.toDomain(recipeForm);
        return recipeMapper.toView(recipe);
    }

    public RecipeView getById(final String id) {
        final var recipe = findById(id);
        return recipeMapper.toView(recipe);
    }

    private Recipe findById(final String id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Recipe [id: %s] not found", id)));
    }

    public List<RecipeView> getAll(final Pageable pageable) {
        final var recipes = recipeRepository.findAll(pageable);

        if(!recipes.hasContent()) {
            return List.of();
        }

        return convertToListOfDTO(recipes.getContent());
    }

    public void delete(final String id) {
        recipeRepository.deleteById(id);
    }

    public void update(final String id, final RecipeForm recipeForm) {
        final var registeredRecipe = findById(id);
        final var recipe = recipeMapper.toDomain(registeredRecipe.getId(), recipeForm);

        recipeRepository.save(recipe);
    }

    public List<RecipeView> getByIngredients(final List<String> ingredients) {
        final var recipes = recipeRepository.findByIngredients(ingredients);
        return convertToListOfDTO(recipes);
    }

    private List<RecipeView> convertToListOfDTO(final List<Recipe> recipes) {
        return recipes.stream().map(recipeMapper::toView).toList();
    }
}
