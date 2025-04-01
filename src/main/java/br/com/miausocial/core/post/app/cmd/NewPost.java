package br.com.miausocial.core.post.app.cmd;

import br.com.miausocial.types.Location;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class NewPost {

    private String title;
    private String body;
    private final List<String> imageUrl = new ArrayList<>();
    private Location location;

    public void addImgsUrls(List<String> imageUrls) {
        this.imageUrl.addAll(imageUrls);
    }

}
