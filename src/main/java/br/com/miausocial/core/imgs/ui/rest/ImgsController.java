package br.com.miausocial.core.imgs.ui.rest;

import br.com.miausocial.core.imgs.app.ImgsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/i")
@RequiredArgsConstructor
public class ImgsController {

    private final ImgsService imgsService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<List<String>> upload(@RequestPart("images") List<MultipartFile> images) {
        List<String> keys = imgsService.uploadImages(images);
        return ResponseEntity.ok(keys);
    }

    @GetMapping(value = "/{key}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String key) {
        byte[] imgBytes = imgsService.getImageBytes(key);
        return ResponseEntity.ok(imgBytes);
    }
}
