package br.com.miausocial.core.profile.repo;

import br.com.miausocial.core.profile.domain.Profile;
import br.com.miausocial.core.user.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, UUID>, 
                                         PagingAndSortingRepository<Profile, UUID>, 
                                         JpaRepository<Profile, UUID>, 
                                         JpaSpecificationExecutor<Profile> {
    Optional<Profile> findByUser(AppUser user);
} 