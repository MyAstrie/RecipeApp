package my.recipeapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class GreetingController {

    @GetMapping("/")
    public String helloWeb() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String getInfo() {
        return "Андрей; Приложение для сайта рецептов; " + LocalDate.now() + "; Приложение в котором можно будет посмотреть и добавить рецепт";
    }
}
