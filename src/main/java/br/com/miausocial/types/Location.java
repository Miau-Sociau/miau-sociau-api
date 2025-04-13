package br.com.miausocial.types;


import br.com.miausocial.infra.ddd.ValueObject;
import lombok.*;


@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Location implements ValueObject {
    @NonNull
    private Double latitude;
    @NonNull
    private Double longitude;
    @NonNull
    private String address;
    public static Location of(String address, Double latitude, Double longitude) {
        return new Location(latitude,longitude,address);
    }
}
