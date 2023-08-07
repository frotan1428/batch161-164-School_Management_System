package com.tpe.domain;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books") // Specify the MongoDB collection name
public class Book {

    @Id
    private String id; // Use String as the ID type for MongoDB

    @NotNull(message = "First name cannot be null.")
    @NotBlank(message = "First name cannot be white space")
    private String title;

    private String author;
    private String publicationDate;

    private List<Teacher> teachers = new ArrayList<>();

    // Constructors, getters, setters, and other methods
}
