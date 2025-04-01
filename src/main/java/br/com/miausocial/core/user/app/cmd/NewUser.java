package br.com.miausocial.core.user.app.cmd;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Getter
public class NewUser {
    @NotBlank(message = "O parâmetro 'logradouro' é obrigatório!")
    private String firstName;
    private String lastName;
    @NotBlank(message = "O parâmetro 'logradouro' é obrigatório!")
    private String email;
    @NotBlank(message = "O parâmetro 'logradouro' é obrigatório!")
    private String password;
    @NotBlank(message = "O parâmetro 'logradouro' é obrigatório!")
    private String userName;
}
