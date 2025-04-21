package br.com.miausocial.core.user.domain;

import br.com.miausocial.infra.ddd.AbstractAggregateRoot;
import br.com.miausocial.shared.Email;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class AppUser extends AbstractAggregateRoot<UUID> {
    @NonNull
    private String firstName;
    private String lastName;
    @Embedded
    @NonNull
    private Email email;
    @NonNull
    private String password;
    @NonNull
    private String userName;

    @Builder
    private AppUser(@NonNull String firstName, String lastName, @NonNull Email email, @NonNull String password, @NonNull String userName) {
        super(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

}