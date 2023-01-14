package my.recipeapp.controllers;

import lombok.RequiredArgsConstructor;
import my.recipeapp.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import my.recipeapp.model.Recipe;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/recipe")
@RequiredArgsConstructor
@Validated
public class RecipeController {

    @Autowired
    private final RecipeService recipeService;

    @GetMapping
    public String start(){
        return "Страница рецепта";
    }

    @PostMapping("/add")
    @Validated(Recipe.class)
    public Recipe addRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.add(recipe);
    }

    @PutMapping("/update/{id}")
    @Validated(Recipe.class)
    public Recipe update(@Valid @RequestBody Recipe newRecipe, @PathVariable Long id) {
        return recipeService.update(newRecipe, id);
    }

    @GetMapping("/find/{id}")
    public Optional<Recipe> findRecipeById(@PathVariable long id) {
        return recipeService.getRecipeById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public Map<Long,Recipe> getRecipes(){
        return recipeService.getRecipes();
    }
}
