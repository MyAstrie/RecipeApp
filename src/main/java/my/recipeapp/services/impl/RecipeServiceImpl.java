package my.recipeapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import my.recipeapp.model.Recipe;
import my.recipeapp.repository.RecipeRepository;
import my.recipeapp.services.RecipeService;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository<Long, Recipe> recipeRepository;

    @Override
    public Recipe add(Recipe recipe){
        if(!recipeRepository.existsById(recipe.getId())) {
            return recipeRepository.add(recipe);
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

        return "рецепт удален !! " + id;
    }

    @Override
    public Recipe update(Recipe newRecipe, Long id) {
        if(recipeRepository.findById(id).isPresent()){
            throw new RuntimeException("Вы пытаетесь обновить несуществующий рецепт");
        }

        return recipeRepository.update(newRecipe, id);
    }

    @Override
    public Recipe deleteById(Long id) {
        return recipeRepository.deleteById(id);
    }
}
