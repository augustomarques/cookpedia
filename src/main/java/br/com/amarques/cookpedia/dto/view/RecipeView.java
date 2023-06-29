package br.com.amarques.cookpedia.dto.view;

import java.util.List;

public record RecipeView(String id, String name, String wayOfDoing, List<String> ingredients) {

}
