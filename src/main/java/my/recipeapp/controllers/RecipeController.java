package my.recipeapp.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import my.recipeapp.model.Recipe;
import my.recipeapp.services.FileService;
import my.recipeapp.services.RecipeService;
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
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/recipe")
@RequiredArgsConstructor
@Api(tags = "recipe")
public class RecipeController {

    @Autowired
    private final RecipeService recipeService;

    private final FileService filesService;

    @GetMapping
    public String start(){
        return "Страница рецепта";
    }

    @ApiOperation(value = "Обновить файл рецептов")
    @PostMapping(value = "/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IOException {
        if(!Objects.equals(file.getOriginalFilename(), "recipe.json")) {
            throw new RuntimeException("Выберите файл с названием recipe.json");
        }

        filesService.uploadFile(file);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Скачать файл рецептов")
    @GetMapping(value = "/files/download/json")
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {
        File file = filesService.getRecipesFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipe.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @ApiOperation(value = "Скачать файл рецептов")
    @GetMapping(value = "/files/download/docx")
    public ResponseEntity<InputStreamResource> downloadDocxFile() throws IOException {
        File file = filesService.getRecipeDocx(recipeService.getRecipes());

        if (file.exists()) {
            InputStream is = new FileInputStream(file);
            InputStreamResource resource = new InputStreamResource(is);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());

            return ResponseEntity.ok().headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
