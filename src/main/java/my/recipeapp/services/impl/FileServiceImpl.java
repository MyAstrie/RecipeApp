package my.recipeapp.services.impl;

import my.recipeapp.model.Ingredient;
import my.recipeapp.model.Recipe;
import my.recipeapp.services.FileService;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class FileServiceImpl implements FileService {

    @Value("${path.to.files}")
    private String dataPath;

    @Value("${name.of.ingredients.data}")
    private String dataFileIngredientsName;

    @Value("${name.of.recipe.data}")
    private String dataFileRecipesName;

    @Value("${name.of.recipe.docx}")
    private String dataDocxRecipesName;


    @Override
    public boolean saveToFileIngredients(String json) {
        try {
            cleanDataFileIngredients();
            Files.writeString(Path.of(dataPath, dataFileIngredientsName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean saveToFileRecipes(String json) {
        try {
            cleanDataFileRecipes();
            Files.writeString(Path.of(dataPath, dataFileRecipesName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String readFromFileIngredients() {
        try {
            return Files.readString(Path.of(dataPath, dataFileRecipesName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String readFromFileRecipes() {
        try {
            return Files.readString(Path.of(dataPath, dataFileRecipesName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean cleanDataFileIngredients() {
        try {
            Path path = Path.of(dataPath, dataFileIngredientsName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean cleanDataFileRecipes() {
        try {
            Path path = Path.of(dataPath, dataFileRecipesName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean cleanDocxFileRecipes() {
        try {
            Path path = Path.of(dataPath, dataDocxRecipesName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        Path filePath = Path.of(dataPath,file.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
    }

    @Override
    public File getIngredientsFile() {
        return new File(dataPath + "/" + dataFileIngredientsName);
    }

    @Override
    public File getRecipesFile() {
        return new File(dataPath + "/" + dataFileRecipesName);
    }

    @Override
    public File getRecipeDocx(Map<Long, Recipe> recipeRepository){
        saveDocxRecipes(recipeRepository);
        return new File(dataPath + "/" + dataDocxRecipesName);
    }

    private boolean saveDocxRecipes(Map<Long, Recipe> recipeRepository) {
        try {
            Path path = Path.of(dataPath, dataDocxRecipesName);
            Files.deleteIfExists(path);
            Files.createFile(path);

            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();

            for (Map.Entry<Long, Recipe> entry : recipeRepository.entrySet()) {
                mainDocumentPart.addStyledParagraphOfText("Title", entry.getValue().getName());
                mainDocumentPart.addParagraphOfText("Время приготовления: " + entry.getValue().getTimeToCook() + " минут");

                mainDocumentPart.addParagraphOfText("Ингредиенты:");
                int iterations = 1;
                for (Ingredient ingredient : entry.getValue().getIngredients()) {
                    mainDocumentPart.addParagraphOfText(iterations++ + ") " + ingredient.getName() + " " + ingredient.getQuantity() +
                            " " + ingredient.getMeasurementUnit());
                }

                mainDocumentPart.addParagraphOfText("Инструкция приготовления:");
                iterations = 1;
                for (String step : entry.getValue().getSteps()) {
                    mainDocumentPart.addParagraphOfText(iterations++ + ") " + step);
                }
            }

            File file = new File(String.valueOf(path));
            wordMLPackage.save(file);
            return true;
        } catch (IOException e) {
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}