package com.scm.smart_contact_manager.forms;

import com.scm.smart_contact_manager.validators.ValidFile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "name is required")
    private String name;

    @Email(message = "invalid email address")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;

    @NotBlank(message = "address is required")
    private String address;

    private String description;

    private boolean favorite;

    private String websiteLink;

    private String linkedInLink;

    // create annotation to validate file

    @ValidFile(message = "Invalid File")
    private MultipartFile contactImage;

    private String picture;


}
