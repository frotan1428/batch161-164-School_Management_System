package com.tpe.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @NotNull(message = "Title name cannot be null.")
    @NotBlank(message = "Title name cannot be whitSpace ")
    @Size(min = 4,max = 25,message = "First name '${validatedValue}' must be between {min} and {max} ")
     private String title;
     private String author;
     private String publicationDate;

     private List<Teacher> teachers= new ArrayList<>();


}
