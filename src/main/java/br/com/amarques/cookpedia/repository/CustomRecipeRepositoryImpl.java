package br.com.amarques.cookpedia.repository;

import br.com.amarques.cookpedia.domain.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class CustomRecipeRepositoryImpl implements CustomRecipeRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Recipe> findByIngredients(final List<String> ingredients) {
        final Criteria[] criterias = ingredients.stream()
                .map(ingredient -> Criteria.where("ingredients")
                        .regex(Pattern.compile(".*" + ingredient + ".*", Pattern.CASE_INSENSITIVE)))
                .toArray(Criteria[]::new);

        return mongoTemplate.find(Query.query(new Criteria().andOperator(criterias)), Recipe.class);
    }
}
