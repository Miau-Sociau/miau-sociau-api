package br.com.miausocial.core.post.app.cmd;

import lombok.*;
import org.springframework.lang.Nullable;

import java.util.List;

@NoArgsConstructor
@Getter
@Data
public class NewPost {

    private String title;
    private String body;
    @Nullable
    private List<String> imgsUrls;
    private double latitude;
    private double longitude;
    private String address;

    public void addImgsUrls(List<String> imageUrls) {
        if (imageUrls == null) return;
        if (this.imgsUrls == null){
            this.imgsUrls = imageUrls;
            return;
        }
            this.imgsUrls.addAll(imageUrls);
    }

}
