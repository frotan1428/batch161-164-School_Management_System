package com.tpe.dto;

import com.tpe.domain.Student;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

//import javax.validation.constraints.Email;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String id;

    @NotBlank(message = "First name cannot be null.")
    @NotBlank(message = "First name cannot be white space")
    @Size(min = 2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max}")
    private String name;

    private String lastName;

    @Email(message = "Provide a valid e-mail.")
    private String email;

    private String phoneNumber;

    private LocalDateTime registerDate = LocalDateTime.now();

    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.lastName = student.getLastName();
        this.phoneNumber = student.getPhoneNumber();
        this.email = student.getEmail();
        this.registerDate = getRegisterDate();
    }
}
