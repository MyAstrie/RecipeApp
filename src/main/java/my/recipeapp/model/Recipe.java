package my.recipeapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Названме рецепта не может быть пустым")
    private String name;

    @Positive(message = "Время готовки должно быть положительно")
    private Integer timeToCook;

    @ElementCollection
    @Valid
    private List<@Valid Ingredient> ingredients;

    @ElementCollection
    private List<String> steps;
}
