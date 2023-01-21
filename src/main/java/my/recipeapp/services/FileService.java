package my.recipeapp.services;

import my.recipeapp.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface FileService {

    boolean saveToFileIngredients(String json);

    boolean saveToFileRecipes(String json);

    String readFromFileIngredients();

    String readFromFileRecipes();

    void uploadFile(MultipartFile file) throws IOException;

    File getIngredientsFile();

    File getRecipesFile();

    File getRecipeDocx(Map<Long, Recipe> recipeRepository);
}
