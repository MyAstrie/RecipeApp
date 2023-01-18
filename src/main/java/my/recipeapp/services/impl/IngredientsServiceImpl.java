package my.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import my.recipeapp.model.Ingredient;
import my.recipeapp.repository.IngredientsRepository;
import my.recipeapp.services.FileService;
import my.recipeapp.services.IngredientsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientsServiceImpl implements IngredientsService {

    private IngredientsRepository<Long, Ingredient> ingredientsRepository;
    private final FileService filesService;

    @PostConstruct
    public void init() {
        ingredientsRepository = new IngredientsRepository<>();
        readFromFileIngredients();
    }

    @Override
    public Ingredient add(Ingredient ingredient) {
        if (!ingredientsRepository.existsById(ingredient.getId())) {
            ingredientsRepository.add(ingredient);
            saveToFileIngredients();
            return ingredientsRepository.get(ingredient.getId());
        } else {
            throw new RuntimeException("Данный инградиент уже существует");
        }
    }

    @Override
    public Optional<Ingredient> getIngredientById(Long id) {
        Optional<Ingredient> ingredient = ingredientsRepository.findById(id);
        if (ingredient.isEmpty()) {
            throw new RuntimeException("Ингредиента не существует");
        }

        return ingredient;
    }

    @Override
    public Map<Long, Ingredient> getIngredients() {
        return ingredientsRepository.getIngredients();
    }

    @Override
    public String deleteIngredient(Long id) {
        ingredientsRepository.remove(id);
        saveToFileIngredients();
        return "ингредиент удален !! " + id;
    }

    @Override
    public Ingredient update(Ingredient newIngredient, Long id) {
        if (ingredientsRepository.findById(id).isPresent()) {
            throw new RuntimeException("Вы пытаетесь обновить несуществующий ингредиент");
        }

        var temIng = ingredientsRepository.update(newIngredient, id);
        saveToFileIngredients();
        return temIng;
    }

    @Override
    public Ingredient deleteById(Long id) {
        var tempIng = ingredientsRepository.deleteById(id);
        saveToFileIngredients();
        return tempIng;
    }

    private void saveToFileIngredients() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientsRepository);
            filesService.saveToFileIngredients(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFileIngredients() {
        String json = filesService.readFromFileIngredients();
        try {
            ingredientsRepository = new ObjectMapper().readValue(json, new TypeReference<>(){
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
