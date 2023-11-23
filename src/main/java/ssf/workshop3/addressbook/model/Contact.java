package ssf.workshop3.addressbook.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contact {

    @Size(min = 3, max = 64, message = "Length must be between 3 to 64 characters")
    private String name;

    @Email(message = "Must be a valid Email")
    private String email;

    @Pattern(regexp = "[0-9]{7,}", message = "Must contain at least 7 digits")
    private String phone;

    @Past(message = "Date of birth must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")  
    private Date birthday;

    private String id;

    public Contact(String name, String email, String phone, Date birthday) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.id = null;
    }
}
