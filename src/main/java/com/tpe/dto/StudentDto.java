package com.tpe.dto;

import com.tpe.domain.Student;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {


    private String id;

    @NotNull(message = "First name cannot be null.")
    @NotBlank(message = "First name cannot be whitSpace ")
    @Size(min = 4,max = 25,message = "First name '${validatedValue}' must be between {min} and {max} ")
    private String name;


    private String lastName;

    @Email(message = "Please provide a valid email ...")
    private String email;

    private String phoneNumber;

    private LocalDateTime registerDate= LocalDateTime.now();

    public StudentDto(Student student) {
        this.id=student.getId();
        this.name=student.getName();
        this.lastName=student.getLastName();
        this.email=student.getEmail();
        this.phoneNumber=student.getPhoneNumber();
        this.registerDate=getRegisterDate();

    }
}
