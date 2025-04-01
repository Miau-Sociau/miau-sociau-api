package br.com.miausocial.core.post.ui.rest;

import br.com.miausocial.core.post.app.PostService;
import br.com.miausocial.core.post.app.cmd.NewPost;
import br.com.miausocial.core.post.domain.Post;
import br.com.miausocial.infra.storage.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

    private final PostService service;
    private StorageService storageService;


    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(summary = "Cria Post com Imagens", description = "Cria um post e envia imagens para o S3.")
    public ResponseEntity<UUID> newPost(
            @RequestPart("data") @NonNull NewPost cmd,
             @RequestPart(value = "images", required = false) List<MultipartFile> images) throws Exception {

        List<String> imageUrls = images.stream()
                .map(image -> storageService.uploadFile(image))
                .toList();

        cmd.addImgsUrls(imageUrls);
        UUID id = service.handle(cmd);

        return ResponseEntity.ok(id);
    }
    @GetMapping
    public ResponseEntity<List<Post>> getAllUsers() {
        return ResponseEntity.ok(service.findAll());
    }



}
