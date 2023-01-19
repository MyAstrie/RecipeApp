package my.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import my.recipeapp.model.Recipe;
import my.recipeapp.repository.RecipeRepository;
import my.recipeapp.services.FileService;
import my.recipeapp.services.RecipeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository<Long, Recipe> recipeRepository;
    private final FileService fileService;

    @PostConstruct
    public void init() {
        recipeRepository = new RecipeRepository<>();
        readFromFileRecipes();
    }

    @Override
    public Recipe add(Recipe recipe){
        if(!recipeRepository.existsById(recipe.getId())) {
            recipeRepository.add(recipe);
            saveToFileRecipes();
            return  recipeRepository.get(recipe.getId());
        } else {
            throw new RuntimeException("Данный рецепт уже существует");
        }
    }

    @Override
    public Optional<Recipe> getRecipeById(Long id){
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isEmpty()){
            throw new RuntimeException("Рецепт не существует");
        }

        return recipe;
    }

    @Override
    public Map<Long, Recipe> getRecipes(){
        return recipeRepository.getRecipes();
    }

    @Override
    public String deleteRecipe(Long id){
        if(recipeRepository.remove(id) == null){
            throw new RuntimeException("Рецепт не существует");
        }

        saveToFileRecipes();
        return "рецепт удален !! " + id;
    }

    @Override
    public Recipe update(Recipe newRecipe, Long id) {
        if(recipeRepository.findById(id).isPresent()){
            throw new RuntimeException("Вы пытаетесь обновить несуществующий рецепт");
        }

        var tempRec = recipeRepository.update(newRecipe, id);
        saveToFileRecipes();
        return tempRec;
    }

    @Override
    public Recipe deleteById(Long id) {
        var tempRec = recipeRepository.deleteById(id);
        saveToFileRecipes();
        return tempRec;
    }

    private void saveToFileRecipes() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeRepository);
            fileService.saveToFileRecipes(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFileRecipes() {
        String json = fileService.readFromFileRecipes();
        try {
            recipeRepository = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}