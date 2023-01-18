package my.recipeapp.repository;

import my.recipeapp.model.Recipe;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

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
        return (HashMap<K, V>) super.clone();
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
        return (Recipe) super.remove(id);
    }
}