package com.tpe.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "teachers") // Specify the MongoBD collection name
public class Teacher {

    @Id
    private String id; // Use String as the ID type for MongoDBDB

    @NotNull(message = "First name cannot be null.")
    @NotBlank(message = "First name cannot be white space")
    @Size(min = 2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max} characters")
    private String name;

    private String lastName;

    @Email(message = "Provide a valid e-mail.")
    private String email;

    private String phoneNumber;

    private Date registerDate = new Date();

    private List<Book> books = new ArrayList<>();


}
