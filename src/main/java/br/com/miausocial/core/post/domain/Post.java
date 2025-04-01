package br.com.miausocial.core.post.domain;

import br.com.miausocial.infra.ddd.AbstractEntity;
import br.com.miausocial.types.Location;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post extends AbstractEntity<UUID> {
    private String title;
    private String body;
    private List<String> imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Embedded
    private Location location;

    private Post(String title, String body, List<String> imageUrl, Location location) {
        if ((title == null || title.trim().isEmpty()) && (body == null || body.trim().isEmpty()) && (imageUrl == null || imageUrl.isEmpty())) {
            throw new IllegalArgumentException("Cannot create a post without a title, body or imageUrl");
        }
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
        this.location = location;
    }


    public PostForm update(){
        return new PostForm( (form) ->{
            this.title = form.getTitle();
            this.body = form.getBody();
            this.imageUrl = form.getImageUrl();
            this.updatedAt = LocalDateTime.now();
            this.location = form.getLocation();

        });
    }

}
