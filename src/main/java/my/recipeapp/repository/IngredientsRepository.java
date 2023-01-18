package my.recipeapp.repository;

import my.recipeapp.model.Ingredient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class IngredientsRepository<K,V> extends HashMap<K,V> {

    public boolean existsById(K id){
            return super.containsKey(id);
    }

    public Optional<V> findById(K id) {
        V value = super.get(id);
        return Optional.ofNullable(value);
    }

    public HashMap<K,V> getIngredients(){
        return (HashMap<K, V>) super.clone();
    }

    public Ingredient add(Ingredient i){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(i.getId());
        ingredient.setName(i.getName());
        ingredient.setQuantity(i.getQuantity());
        ingredient.setMeasurementUnit(i.getMeasurementUnit());
        super.put((K) i.getId(), (V) ingredient);
        return ingredient;
    }

    public Ingredient rename(Long id){
        return (Ingredient) super.remove(id);
    }

    public Ingredient update(Ingredient newIngredient, Long id) {
        return (Ingredient) super.put((K) id, (V) newIngredient);
    }

    public Ingredient deleteById(Long id) {
        return (Ingredient) super.remove(id);
    }
}
