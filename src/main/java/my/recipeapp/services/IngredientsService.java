package my.recipeapp.services;

import my.recipeapp.model.Ingredient;

import java.util.Map;
import java.util.Optional;

public interface IngredientsService {

    Ingredient add(Ingredient ingredient);

    Optional<Ingredient> getIngredientById(Long id);

    Map<Long, Ingredient> getIngredients();

    public String deleteIngredient(Long id);

    public Ingredient update(Ingredient newIngredient, Long id);

    public Ingredient deleteById(Long id);
}
