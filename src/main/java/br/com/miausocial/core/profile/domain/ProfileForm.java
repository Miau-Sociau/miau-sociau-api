package br.com.miausocial.core.profile.domain;

import br.com.miausocial.shared.Image;
import lombok.*;

import java.util.function.Consumer;

@Getter
@Setter
@RequiredArgsConstructor
public class ProfileForm {
    private final Consumer<ProfileForm> action;

    private Image profilePicture;
    private Image banner;
    private String bio;

    public void apply() {
        action.accept(this);
    }
} 