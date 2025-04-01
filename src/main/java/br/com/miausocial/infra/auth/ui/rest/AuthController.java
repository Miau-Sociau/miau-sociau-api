package br.com.miausocial.infra.auth.ui.rest;

import br.com.miausocial.core.user.app.UserService;
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
@RequestMapping("/api/auth")
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
    public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password) {
        Optional<AppUser> userOptional = userRepository.findOne(findByUsernameOrEmail(login));

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Usuário não encontrado!");
        }
        AppUser user = userOptional.get();

        if(!passwordEncoder.matches(password, user.getPassword())) return ResponseEntity.status(401).body("Senha incorreta!");

        return ResponseEntity.ok(jwtUtil.generateToken(user));
    }
}
