package br.com.miausocial.core.profile.domain;

import br.com.miausocial.core.user.domain.AppUser;
import br.com.miausocial.infra.ddd.AbstractAggregateRoot;
import br.com.miausocial.shared.Image;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends AbstractAggregateRoot<UUID> {
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Embedded
    private Image profilePicture;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "url", column = @Column(name = "banner_url"))
    })
    private Image banner;

    @Column(length = 500)
    private String bio;

    @Builder
    private Profile(AppUser user, Image profilePicture, Image banner, String bio) {
        super(UUID.randomUUID());
        this.user = user;
        this.profilePicture = profilePicture;
        this.banner = banner;
        this.bio = bio;
    }

    public static Profile of(AppUser user){
        return Profile.builder()
            .user(user)
            .bio("")
            .profilePicture(Image.of(""))
            .banner(Image.of(""))
            .build();
    }

    public ProfileForm update() {
        return new ProfileForm(form -> {
            this.profilePicture = form.getProfilePicture();
            this.banner = form.getBanner();
            this.bio = form.getBio();
        });
    }
} 