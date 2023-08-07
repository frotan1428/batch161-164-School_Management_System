package com.tpe.dto;
import com.tpe.domain.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {


    private String id;
    @NotNull(message = "First name cannot be null.")
    @NotBlank(message = "First name cannot be white space")
    private String title;

    private String author;

    private String publicationDate;


    public BookDto(Book book) {

        this.id=book.getId();
        this.title=book.getTitle();
        this.author=book.getAuthor();
        this.publicationDate=book.getPublicationDate();
    }
}
