package storespring.dto;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Size(min = 2, max = 14, message = "Імʼя обліку має бути від 2 до 14 символів")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9А-Яа-я]{8,20}$",
            message = "Введіть коректний пароль \n(8-20 символов |А-Яа-я,A-Za-z,0-9)")
    private String password;
}