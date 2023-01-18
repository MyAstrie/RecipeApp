package my.recipeapp.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import my.recipeapp.model.Ingredient;
import my.recipeapp.services.IngredientsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
@Api(tags = "ingredients")
public class IngredientsController {

    private final IngredientsService ingredientsService;

    @GetMapping
    public String start(){
        return "Страница ингредиента";
    }

    @ApiOperation(value = "Добавить новый ингредиент")
    @PostMapping("/add")
    @Validated(Ingredient.class)
    public Ingredient addIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ingredientsService.add(ingredient);
    }

    @ApiOperation(value = "Обновить ингредиент")
    @PutMapping("/update/{id}")
    public Ingredient update(@Valid @RequestBody Ingredient ingredient, @PathVariable("id") Long id) {
        return ingredientsService.update(ingredient, id);
    }

    @ApiOperation(value = "Посмотреть ингредиент по ID", response = Ingredient.class)
    @GetMapping("/find/{id}")
    public Optional<Ingredient> findRecipeById(@ApiParam(value = "ID") @PathVariable long id) {
        return ingredientsService.getIngredientById(id);
    }

    @ApiOperation(value = "Удаление ингедиента по ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ingredientsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Показать все ингредиенты", response = Iterable.class)
    @GetMapping("/all")
    public Map<Long, Ingredient> getIngredients(){
        return ingredientsService.getIngredients();
    }
}
