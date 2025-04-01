package br.com.miausocial.core.post.repo;

import br.com.miausocial.core.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;
public interface PostRepository extends CrudRepository<Post, UUID>, PagingAndSortingRepository<Post, UUID>, JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {
}
