package br.com.miausocial.core.post.app;

import br.com.miausocial.core.post.app.cmd.NewPost;
import br.com.miausocial.core.post.domain.Post;
import br.com.miausocial.core.post.repo.PostRepository;
import br.com.miausocial.shared.Image;
import br.com.miausocial.shared.Location;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Log
@Service
@Transactional(propagation = REQUIRES_NEW)
public class PostService {

    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    @NonNull
    public UUID handle(NewPost cmd) {
        List<Image> imgs = new ArrayList<>();
        if (cmd.getImgsUrls() != null) {
            for (String imgUrl : cmd.getImgsUrls()) {
                imgs.add(Image.of(imgUrl));
            }
        }

        Post post = Post.builder()
                .title(cmd.getTitle())
                .body(cmd.getBody())
                .imageUrl(imgs)
                .location(Location.of(cmd.getAddress(), cmd.getLatitude(), cmd.getLongitude()))
                .build();

        return repo.save(post).id();
    }

    @NonNull
    public List<Post> findAll() {
        return repo.findAll();
    }
}
