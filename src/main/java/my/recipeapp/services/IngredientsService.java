package my.recipeapp.services;

import my.recipeapp.model.Ingredient;

import java.util.Map;
import java.util.Optional;

public interface IngredientsService {

    Ingredient add(Ingredient ingredient);

    Optional<Ingredient> getIngredientById(Long id);

    Map<Long, Ingredient> getIngredients();

    String deleteIngredient(Long id);

    Ingredient update(Ingredient newIngredient, Long id);

    Ingredient deleteById(Long id);
}
