package br.com.miausocial.core.user.ui.rest;
import br.com.miausocial.shared.ErrorResponse;
import br.com.miausocial.core.user.app.UserService;
import br.com.miausocial.core.user.app.cmd.LoginCredentials;
import br.com.miausocial.core.user.app.cmd.NewUser;
import br.com.miausocial.core.user.domain.AppUser;
import br.com.miausocial.core.user.repo.UserRepository;
import br.com.miausocial.infra.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static br.com.miausocial.core.user.repo.spec.Spec.findByUsernameOrEmail;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Operation(summary = "Cria Usuario", description = "Cria usuario.")
    @ApiResponse(responseCode = "201", description = "registrado com sucesso", content = @Content(mediaType = "string"))
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<UUID> newUser(@RequestBody @NonNull NewUser cmd) throws Exception {
        UUID id = userService.handle(cmd);

        return ResponseEntity.ok(id);
    }
    @PostMapping("/signing")
    public ResponseEntity<?> login(@RequestBody @NonNull LoginCredentials cmd) {
        Optional<AppUser> userOptional = userRepository.findOne(findByUsernameOrEmail(cmd.getEmail()));

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401)
                    .body(new ErrorResponse("Usuário não encontrado!", 401));
        }

        AppUser user = userOptional.get();

        if (!passwordEncoder.matches(cmd.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401)
                    .body(new ErrorResponse("Senha incorreta!", 401));
        }

        return ResponseEntity.ok(jwtUtil.generateToken(user));
    }
}
