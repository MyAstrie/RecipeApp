package my.recipeapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import my.recipeapp.model.Ingredient;
import my.recipeapp.repository.IngredientsRepository;
import my.recipeapp.services.IngredientsService;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientsServiceImpl implements IngredientsService {

    @Autowired
    private IngredientsRepository<Long, Ingredient> ingredientsRepository;

    @Override
    public Ingredient add(Ingredient ingredient) {
        if(!ingredientsRepository.existsById(ingredient.getId())){
            ingredientsRepository.add(ingredient);
            return ingredientsRepository.get(ingredient.getId());
        } else {
            throw new RuntimeException("Данный инградиент уже существует");
        }
    }

    @Override
    public Optional<Ingredient> getIngredientById(Long id) {
        Optional<Ingredient> ingredient = ingredientsRepository.findById(id);
        if(ingredient.isEmpty()){
            throw new RuntimeException("Ингредиента не существует");
        }
        return ingredient;
    }

    @Override
    public Map<Long, Ingredient> getIngredients(){
        return ingredientsRepository.getIngredients();
    }

    @Override
    public String deleteIngredient(Long id) {
        ingredientsRepository.remove(id);
        return "product removed !! " + id;
    }

    @Override
    public Ingredient update(Ingredient newIngredient, Long id) {
        if(ingredientsRepository.findById(id).isPresent()) {
            throw new RuntimeException("Вы пытаетесь обновить несуществующий ингредиент");
        }

        return ingredientsRepository.update(newIngredient, id);
    }

    @Override
    public Ingredient deleteById(Long id) {
        return ingredientsRepository.deleteById(id);
    }
}
