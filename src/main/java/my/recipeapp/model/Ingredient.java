package my.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Названме инградиента не может быть пустым")
    private String name;

    @Positive(message = "Количество инградиентов должно быть положительным")
    private Integer quantity;

    @NotBlank(message = "Еденица измерения не может быть пустая")
    private String measurementUnit;
}
