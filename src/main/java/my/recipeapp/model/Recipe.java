package my.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @NotBlank(message = "Время готовки не может быть пустым")
    private Integer timeToCook;

    @NotBlank
    @ElementCollection
    @Valid
    private List<Ingredient> ingredients;

    @NotBlank
    @ElementCollection
    @Valid
    private List<String> steps;
}
