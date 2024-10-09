package com.scm.smart_contact_manager.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "username is required")
    @Size(min = 3, message = "min 3 character is required")
    private String name;

    @Email(message = "invalid email address")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6, message = "min 6 characters is required")
    private String password;

    @NotBlank(message = "about is required")
    private String about;

    @Size(min = 8, max = 12, message = "invalid phone number")
    private String phoneNumber;
}
