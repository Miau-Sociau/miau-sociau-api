package br.com.miausocial.core.user.repo;

import br.com.miausocial.core.user.domain.AppUser;
import br.com.miausocial.shared.Email;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<AppUser, UUID>, PagingAndSortingRepository<AppUser, UUID>, JpaRepository<AppUser, UUID>, JpaSpecificationExecutor<AppUser> {
    boolean existsByEmail(@NonNull Email email);

    boolean existsByUserName(@NonNull String userName);

    Optional<AppUser> findByUserName(@NonNull String userName);
}
