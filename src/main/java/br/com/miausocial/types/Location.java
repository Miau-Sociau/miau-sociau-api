package br.com.miausocial.types;


import br.com.miausocial.infra.ddd.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class Location implements ValueObject {
    @NonNull
    private Double latitude;
    @NonNull
    private Double longitude;
    @NonNull
    private String address;

    public Location of(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        return this;
    }
}
