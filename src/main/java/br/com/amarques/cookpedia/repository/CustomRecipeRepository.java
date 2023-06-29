package br.com.amarques.cookpedia.repository;

import br.com.amarques.cookpedia.domain.Recipe;

import java.util.List;

public interface CustomRecipeRepository {

    List<Recipe> findByIngredients(List<String> ingredients);
}
