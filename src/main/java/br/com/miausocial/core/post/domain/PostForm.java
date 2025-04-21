package br.com.miausocial.core.post.domain;

import br.com.miausocial.shared.Image;
import br.com.miausocial.shared.Location;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;


@Getter
@Setter
@RequiredArgsConstructor
public class PostForm {
    private final Consumer<PostForm> action;


    private String title;
    private String body;
    private List<Image> imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Location location;

    public void apply() {
        action.accept(this);
    }
}
