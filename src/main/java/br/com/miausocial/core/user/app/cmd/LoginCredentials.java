package br.com.miausocial.core.user.app.cmd;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginCredentials {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
