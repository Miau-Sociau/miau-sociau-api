package br.com.miausocial.shared;

import br.com.miausocial.infra.ddd.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode(of = {"latitude", "longitude"})
@NoArgsConstructor(access = PUBLIC, force = true)
@AllArgsConstructor(access = PRIVATE)
public final class Location implements ValueObject {
    
    private Double latitude;
    private Double longitude;
    private String address;

    public static Location of(String address, Double latitude, Double longitude) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio");
        }

        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude e longitude não podem ser nulos");
        }

        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude deve estar entre -90 e 90 graus");
        }

        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude deve estar entre -180 e 180 graus");
        }

        return new Location(latitude, longitude, address.trim());
    }

    @Override
    public String toString() {
        return String.format("%s (%.6f, %.6f)", address, latitude, longitude);
    }
}
