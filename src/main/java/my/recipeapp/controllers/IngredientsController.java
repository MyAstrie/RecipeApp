package my.recipeapp.controllers;

import lombok.RequiredArgsConstructor;
import my.recipeapp.model.Recipe;
import my.recipeapp.services.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import my.recipeapp.model.Ingredient;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
@Validated
public class IngredientsController {

    @Autowired
    private final IngredientsService ingredientsService;

    @GetMapping
    public String start(){
        return "Страница ингредиента";
    }

    @PostMapping("/add")
    @Validated(Ingredient.class)
    public Ingredient addIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ingredientsService.add(ingredient);
    }

    @PutMapping("/update/{id}")
    @Validated(Ingredient.class)
    public Ingredient update(@Valid @RequestBody Ingredient ingredient, @PathVariable Long id) {
        return ingredientsService.update(ingredient, id);
    }

    @GetMapping("/find/{id}")
    public Optional<Ingredient> findRecipeById(@PathVariable long id) {
        return ingredientsService.getIngredientById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ingredientsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public Map<Long, Ingredient> getIngredients(){
        return ingredientsService.getIngredients();
    }
}
