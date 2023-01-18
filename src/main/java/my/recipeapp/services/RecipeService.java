package my.recipeapp.services;

import my.recipeapp.model.Recipe;

import java.util.Map;
import java.util.Optional;

public interface RecipeService {

    Recipe add(Recipe recipe);

    Optional<Recipe> getRecipeById(Long id);

    Map<Long, Recipe> getRecipes();

    String deleteRecipe(Long id);

    Recipe update(Recipe newRecipe, Long id);

    Recipe deleteById(Long id);
}
