package br.com.miausocial.shared;

import br.com.miausocial.infra.ddd.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.regex.Pattern;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode(of = "email")
@NoArgsConstructor(access = PUBLIC, force = true)
@AllArgsConstructor(access = PRIVATE)
public final class Email implements ValueObject {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private String email;

    public static Email of(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }

        String trimmedEmail = email.trim().toLowerCase();
        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }

        return new Email(trimmedEmail);
    }

    @Override
    public String toString() {
        return email;
    }
}