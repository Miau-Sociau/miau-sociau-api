package br.com.miausocial.core.user.repo;

import br.com.miausocial.core.user.domain.AppUser;
import br.com.miausocial.types.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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
}
