package ru.job4j.job4j_auth.model;


//import jakarta.persistence.*;
import lombok.*;
import ru.job4j.job4j_auth.exception.Operation;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "persons")
public class Person {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id most be not empty", groups = {Operation.OnUpdate.class, Operation.OnDelete.class})
    private int id;
    @NotBlank(message = "Username must be not empty")
    private String username;
    @NotBlank(message = "Password must be not empty")
    private String password;
}
