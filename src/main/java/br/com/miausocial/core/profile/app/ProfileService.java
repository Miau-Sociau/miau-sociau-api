package br.com.miausocial.core.profile.app;

import br.com.miausocial.core.profile.app.cmd.NewProfile;
import br.com.miausocial.core.profile.domain.Profile;
import br.com.miausocial.core.profile.domain.ProfileForm;
import br.com.miausocial.core.profile.repo.ProfileRepository;
import br.com.miausocial.core.user.domain.AppUser;
import br.com.miausocial.shared.Image;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Log
@Service
@Transactional(propagation = REQUIRES_NEW)
public class ProfileService {

    private final ProfileRepository repo;

    public ProfileService(ProfileRepository repo) {
        this.repo = repo;
    }

    @NonNull
    public UUID handle(NewProfile cmd, AppUser user) {
        Profile profile = Profile.builder()
                .user(user)
                .bio(cmd.getBio())
                .profilePicture(cmd.getProfilePictureUrl() != null ? Image.of(cmd.getProfilePictureUrl()) : null)
                .banner(cmd.getBannerUrl() != null ? Image.of(cmd.getBannerUrl()) : null)
                .build();

        return repo.save(profile).id();
    }

    @NonNull
    @Cacheable(value = "profiles", key = "#user.id")
    public Profile findByUser(AppUser user) {
        return repo.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user"));
    }

    @NonNull
    @Cacheable(value = "profiles", key = "#id")
    public Profile findById(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
    }

    @CacheEvict(value = "profiles", key = "#user.id")
    public void updateProfile(AppUser user, NewProfile cmd) {
        Profile profile = findByUser(user);
        
        ProfileForm form = profile.update();
        form.setBio(cmd.getBio());
        if (cmd.getProfilePictureUrl() != null) {
            form.setProfilePicture(Image.of(cmd.getProfilePictureUrl()));
        }
        if (cmd.getBannerUrl() != null) {
            form.setBanner(Image.of(cmd.getBannerUrl()));
        }
        form.apply();
        
        repo.save(profile);
    }

    @CacheEvict(value = "profiles", key = "#user.id")
    public void partialUpdateProfile(AppUser user, NewProfile cmd) {
        Profile profile = findByUser(user);
        
        ProfileForm form = profile.update();
        if (cmd.getBio() != null) {
            form.setBio(cmd.getBio());
        }
        if (cmd.getProfilePictureUrl() != null) {
            form.setProfilePicture(Image.of(cmd.getProfilePictureUrl()));
        }
        if (cmd.getBannerUrl() != null) {
            form.setBanner(Image.of(cmd.getBannerUrl()));
        }
        form.apply();
        
        repo.save(profile);
    }

    @CacheEvict(value = "profiles", key = "#id")
    public void deleteProfile(UUID id, AppUser user) {
        Profile profile = findById(id);
        if (!profile.getUser().equals(user)) {
            throw new IllegalArgumentException("Cannot delete another user's profile");
        }
        repo.delete(profile);
    }
}
