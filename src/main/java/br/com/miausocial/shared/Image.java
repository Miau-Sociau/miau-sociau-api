package br.com.miausocial.shared;

import br.com.miausocial.infra.ddd.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode(of = "url")
@NoArgsConstructor(access = PUBLIC, force = true)
@AllArgsConstructor(access = PRIVATE)
public final class Image implements ValueObject {
    
    private String url;
    private LocalDateTime createdAt;

    public static Image of(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL da imagem não pode ser nula ou vazia");
        }

        String trimmedUrl = url.trim();
        try {
            new URI(trimmedUrl);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URL da imagem inválida: " + url, e);
        }

        return new Image(trimmedUrl, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return url;
    }
}