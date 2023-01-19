package my.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {

    boolean saveToFileIngredients(String json);

    boolean saveToFileRecipes(String json);

    String readFromFileIngredients();

    String readFromFileRecipes();

    void uploadFile(MultipartFile file) throws IOException;

    public File getIngredientsFile();

    public File getRecipesFile();
}
