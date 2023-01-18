package my.recipeapp.services;

public interface FileService {

    boolean saveToFileIngredients(String json);

    boolean saveToFileRecipes(String json);

    String readFromFileIngredients();

    String readFromFileRecipes();
}
