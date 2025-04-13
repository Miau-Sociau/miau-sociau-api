package br.com.miausocial.types;

import br.com.miausocial.infra.ddd.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.NonNull;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;


@Getter
@ToString
@Embeddable
@EqualsAndHashCode()
@NoArgsConstructor(access = PUBLIC, force = true)
@AllArgsConstructor(access = PRIVATE)
public class Image implements ValueObject {
    @NonNull
    private String url;
    @NonNull
    private LocalDateTime createdAt;

    public static Image of(String url) {
        return new Image(url, LocalDateTime.now());
    }


}