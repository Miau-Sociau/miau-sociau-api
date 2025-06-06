package br.com.miausocial.core.post.domain;

import br.com.miausocial.infra.ddd.AbstractEntity;
import br.com.miausocial.shared.Location;
import br.com.miausocial.shared.Image;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;



@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
public class Post extends AbstractEntity<UUID> {
    private String title;
    private String body;
    @ElementCollection
    @CollectionTable(name = "post_images", joinColumns = @JoinColumn(name = "post_id"))
    private  List<Image> imageUrl;
    @Embedded
    private Location location;

    @Builder
    private Post(String title, String body, List<Image> imageUrl, Location location) {
        super(UUID.randomUUID());
        if ((title == null || title.trim().isEmpty()) && (body == null || body.trim().isEmpty()) && (imageUrl == null || imageUrl.isEmpty())) {
            throw new IllegalArgumentException("Cannot create a post without a title, body or imageUrl");
        }

        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public PostForm update(){
        return new PostForm( (form) ->{
            this.title = form.getTitle();
            this.body = form.getBody();
            this.imageUrl.clear();
            this.imageUrl = form.getImageUrl();
            this.location = form.getLocation();
        });
    }
}
