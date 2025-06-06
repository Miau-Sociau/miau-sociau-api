package br.com.miausocial.infra.ddd;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.data.util.ProxyUtils.getUserClass;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.NoArgsConstructor;

/**
 * Base class for entities.
 *
 * @param <ID> the entity ID type.
 */
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
public abstract class AbstractEntity<ID extends UUID>
        implements IdentifiableDomainObject<ID>, ConcurrentDomainObject {

    @Id
    private ID id;

    @Version
    @Nullable
    private Long version;

    /**
     * Copy constructor
     *
     * @param source the entity to copy from.
     */
    protected AbstractEntity(@NonNull AbstractEntity<ID> source) {
        requireNonNull(source, "source must not be null");
        this.id = source.id;
    }

    /**
     * Constructor for creating new entities.
     *
     * @param id the ID to assign to the entity.
     */
    protected AbstractEntity(@NonNull ID id) {
        this.id = requireNonNull(id, "id must not be null");
    }

    @NonNull
    @Override
    public ID id() {
        return id;
    }

    @NonNull
    @Override
    public Long version() {
        return version;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this)
            return true;

        if (obj == null || !getClass().equals(getUserClass(obj)))
            return false;

        var other = (AbstractEntity<?>) obj;

        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }

    @Override
    public String toString() {
        return format("%s[%s]", getClass().getSimpleName(), id);
    }

}