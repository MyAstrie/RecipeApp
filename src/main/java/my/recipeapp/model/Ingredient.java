package my.recipeapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;


@Data
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

    @NotBlank
    @Positive(message = "Количество инградиентов должно быть положительным")
    private Integer quantity;

    @NotBlank(message = "Еденица измерения не может быть пустая")
    private String measurementUnit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ingredient ingredient = (Ingredient) o;
        return id != null && Objects.equals(id, ingredient.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
