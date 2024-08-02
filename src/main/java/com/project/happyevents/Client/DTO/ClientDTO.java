package com.project.happyevents.Client.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private Long id;

    @NotBlank(message = "Error: Name is mandatory")
    private String name;

    @NotBlank(message = "Error: Email is mandatory")
    @Email(message = "Error: Enter correct email")
    private String email;

    @NotBlank(message = "Error: Password is mandatory")
    private String password;

    @NotBlank(message = "Error: Role is mandatory")
    private String role;
}
