package br.com.amarques.cookpedia.mapper;

import br.com.amarques.cookpedia.domain.Recipe;
import br.com.amarques.cookpedia.dto.form.RecipeForm;
import br.com.amarques.cookpedia.dto.view.RecipeView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    Recipe toDomain(RecipeForm recipeForm);

    Recipe toDomain(String id, RecipeForm recipeForm);

    RecipeView toView(Recipe recipe);

}
