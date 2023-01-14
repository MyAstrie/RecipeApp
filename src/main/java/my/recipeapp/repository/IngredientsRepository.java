package my.recipeapp.repository;

import org.springframework.stereotype.Repository;
import my.recipeapp.model.Ingredient;
import my.recipeapp.model.Recipe;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

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
        HashMap<Long, Ingredient> shallowCopy = new HashMap<>();
        Set<Entry<K, V>> entries = super.entrySet();
        for (Entry<K, V> mapEntry : entries) {
            shallowCopy.put((Long) mapEntry.getKey(), (Ingredient) mapEntry.getValue());
        }
        return (HashMap<K, V>) shallowCopy;
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
        return (Ingredient) super.remove((K) id);
    }
}
