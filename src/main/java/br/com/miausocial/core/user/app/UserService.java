package br.com.miausocial.core.user.app;


import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import br.com.miausocial.shared.Email;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


import br.com.miausocial.core.user.app.cmd.NewUser;
import br.com.miausocial.core.user.domain.AppUser;
import br.com.miausocial.core.user.repo.UserRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Log
@Service
@Transactional(propagation = REQUIRES_NEW)
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @NonNull
    public UUID handle(NewUser cmd) throws Exception {

        if (repo.existsByEmail(Email.of(cmd.getEmail()))) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }
        if (repo.existsByUserName((cmd.getUserName()))) {
            throw new IllegalArgumentException("Username já está em uso.");
        }
        try {
        String hashedPassword = passwordEncoder.encode(cmd.getPassword());


            AppUser user = AppUser.builder()
                    .firstName(cmd.getFirstName())
                    .lastName(cmd.getLastName())
                    .email(Email.of(cmd.getEmail()))
                    .password(hashedPassword)
                    .userName(cmd.getUserName())
                    .build();

            return repo.save(user).id();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @NonNull
    public List<AppUser> findAll() {
        return repo.findAll();
    }
}
