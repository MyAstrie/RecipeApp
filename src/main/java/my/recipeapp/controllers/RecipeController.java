package my.recipeapp.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import my.recipeapp.model.Recipe;
import my.recipeapp.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/recipe")
@RequiredArgsConstructor
@Api(tags = "recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public String start(){
        return "Страница рецепта";
    }

    @ApiOperation(value = "Добавить новый рецепт")
    @PostMapping("/add")
    @Validated(Recipe.class)
    public Recipe addRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.add(recipe);
    }

    @ApiOperation(value = "Обновить рецепт")
    @PutMapping("/update/{id}")
    @Validated(Recipe.class)
    public Recipe update(@Valid @RequestBody Recipe newRecipe, @PathVariable Long id) {
        return recipeService.update(newRecipe, id);
    }

    @ApiOperation(value = "Посмотреть рецепта по ID", response = Recipe.class)
    @GetMapping("/find/{id}")
    public Optional<Recipe> findRecipeById(@PathVariable long id) {
        return recipeService.getRecipeById(id);
    }

    @ApiOperation(value = "Удаление рецепта по ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Показать все рецепты", response = Iterable.class)
    @GetMapping("/all")
    public Map<Long,Recipe> getRecipes(){
        return recipeService.getRecipes();
    }
}
