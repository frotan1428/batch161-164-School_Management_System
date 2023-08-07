package com.tpe.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Document: This annotation is used to indicate that a Java class represents
// a MongoDB document
@Document(collection = "students") // Specify the MongoDB collection name
public class Student {

    @Id // This annotation is used to specify the primary identifier field of the document
    private String id; // Use String as the ID type for MongoDB

    @NotNull(message = "First name cannot be null.")
    @NotBlank(message = "First name cannot be white space")
    @Size(min = 2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max} characters")
    private String name;

    @Field(name = "last_name")
    private String lastName;

    @Email(message = "Provide a valid e-mail.")
    private String email;

    private String phoneNumber;

    private Date registerDate = new Date();

    @Transient// This annotation is used to mark fields that should not be persisted to MongoDB
    private String address;

    // Other fields, constructors, getters, setters, and methods...
}
