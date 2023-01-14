package my.recipeapp.repository;

import org.springframework.stereotype.Repository;
import my.recipeapp.model.Ingredient;
import my.recipeapp.model.Recipe;

import java.util.*;

@Repository
public class RecipeRepository<K,V> extends HashMap<K,V> {

    public boolean existsById(K id) {
        return super.containsKey(id);
    }

    public Optional<V> findById(K id) {
        V value = super.get(id);
        return Optional.ofNullable(value);
    }

    public HashMap<K, V> getRecipes() {
        HashMap<Long, Ingredient> shallowCopy = new HashMap<>();
        Set<Entry<K, V>> entries = super.entrySet();
        for (Entry<K, V> mapEntry : entries) {
            shallowCopy.put((Long) mapEntry.getKey(), (Ingredient) mapEntry.getValue());
        }
        return (HashMap<K, V>) shallowCopy;
    }

    public Recipe add(Recipe r) {
        Recipe recipe = new Recipe();
        recipe.setId(r.getId());
        recipe.setName(r.getName());
        recipe.setTimeToCook(r.getTimeToCook());
        recipe.setSteps(r.getSteps());
        super.put((K) recipe.getId(), (V) recipe);
        return recipe;
    }

    public Recipe remove(Long id){
        return (Recipe) super.remove(id);
    }

    public Recipe update(Recipe newRecipe, Long id) {
        return (Recipe) super.put((K) id, (V) newRecipe);
    }

    public Recipe deleteById(Long id) {
        return (Recipe) super.remove((K) id);
    }
}