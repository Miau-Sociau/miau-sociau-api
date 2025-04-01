package br.com.miausocial.core.user.repo.spec;

import br.com.miausocial.core.user.domain.AppUser;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class Spec {

    public static Specification<AppUser> findByUsernameOrEmail(String login) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicateForEmail = criteriaBuilder.equal(root.get("email").get("email"), login);
            return criteriaBuilder.or(predicateForEmail);
        };
    }
}
