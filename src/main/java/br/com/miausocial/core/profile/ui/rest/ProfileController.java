package br.com.miausocial.core.profile.ui.rest;

import br.com.miausocial.core.profile.app.ProfileService;
import br.com.miausocial.core.profile.app.cmd.NewProfile;
import br.com.miausocial.core.profile.domain.Profile;
import br.com.miausocial.core.user.domain.AppUser;
import br.com.miausocial.core.user.repo.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Create Profile", description = "Create a new profile for the authenticated user.")
    @ApiResponse(responseCode = "201", description = "Profile created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UUID> createProfile(
            @RequestBody NewProfile cmd,
            Authentication authentication) {
        AppUser user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        UUID profileId = profileService.handle(cmd, user);
        return ResponseEntity.created(java.net.URI.create("/profiles/" + profileId)).body(profileId);
    }

    @GetMapping("/me")
    @Operation(summary = "Get My Profile", description = "Get the profile of the authenticated user.")
    public ResponseEntity<Profile> getMyProfile(Authentication authentication) {
        AppUser user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Profile profile = profileService.findByUser(user);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Profile by ID", description = "Get a profile by its ID.")
    public ResponseEntity<Profile> getProfile(@PathVariable UUID id) {
        Profile profile = profileService.findById(id);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Profile", description = "Update a profile completely.")
    public ResponseEntity<Void> updateProfile(
            @PathVariable UUID id,
            @RequestBody NewProfile cmd,
            Authentication authentication) {
        AppUser user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        profileService.updateProfile(user, cmd);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially Update Profile", description = "Update specific fields of a profile.")
    public ResponseEntity<Void> partialUpdateProfile(
            @PathVariable UUID id,
            @RequestBody NewProfile cmd,
            Authentication authentication) {
        AppUser user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        profileService.partialUpdateProfile(user, cmd);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Profile", description = "Delete a profile.")
    public ResponseEntity<Void> deleteProfile(
            @PathVariable UUID id,
            Authentication authentication) {
        AppUser user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        profileService.deleteProfile(id, user);
        return ResponseEntity.noContent().build();
    }
} 