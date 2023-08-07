package com.tpe.dto;

import com.tpe.domain.Teacher;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {


    private String id;

    @NotNull(message = "First name cannot be null.")
    @NotBlank(message = "First name cannot be white space")     //  @NotNull, @NotEmpty
    @Size(min = 2, max = 25, message = "First name '${validatedValue}' must be between : {min} and {max}")

    private String name;

    private String lastName;

    @Email(message = "Provide a valid e-mail.")     // @, domain ex. (.com)
    private String email;       // hello, jaceEmail       -->     xyz@yyy.com

    private String phoneNumber;


    private LocalDateTime registerDate = LocalDateTime.now();

    public TeacherDto(Teacher teacher) {

        this.id=teacher.getId();
        this.name=teacher.getName();
        this.lastName=teacher.getLastName();
        this.phoneNumber=teacher.getPhoneNumber();
        this.email=teacher.getEmail();
        this.registerDate=getRegisterDate();


    }
}
