package my.recipeapp.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import my.recipeapp.model.Ingredient;
import my.recipeapp.services.FileService;
import my.recipeapp.services.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
@Api(tags = "ingredients")
public class IngredientsController {

    @Autowired
    private final IngredientsService ingredientsService;

    private final FileService filesService;

    @GetMapping
    public String start(){
        return "Страница ингредиента";
    }

    @ApiOperation(value = "Обновить файл ингредиентов")
    @PostMapping(value = "/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IOException {
        if(!Objects.equals(file.getOriginalFilename(), "ingredients.json")) {
            throw new RuntimeException("Выберите файл с названием ingredients.json");
        }

        filesService.uploadFile(file);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Скачать файл ингредиентов")
    @GetMapping(value = "/files/download")
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {
        File file = filesService.getIngredientsFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ingredients.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
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
