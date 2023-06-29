package br.com.amarques.cookpedia.repository;

import br.com.amarques.cookpedia.domain.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String>, CustomRecipeRepository {

}
