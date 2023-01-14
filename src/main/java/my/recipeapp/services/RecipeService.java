package my.recipeapp.services;

import my.recipeapp.model.Ingredient;
import my.recipeapp.model.Recipe;

import java.util.Map;
import java.util.Optional;

public interface RecipeService {

    Recipe add(Recipe recipe);

    Optional<Recipe> getRecipeById(Long id);

    Map<Long, Recipe> getRecipes();

    public String deleteRecipe(Long id);

    public Recipe update(Recipe newRecipe, Long id);

    public Recipe deleteById(Long id);
}
