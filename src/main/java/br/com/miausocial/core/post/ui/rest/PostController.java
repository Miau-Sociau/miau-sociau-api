package br.com.miausocial.core.post.ui.rest;

import br.com.miausocial.core.post.app.PostService;
import br.com.miausocial.core.post.app.cmd.NewPost;
import br.com.miausocial.core.post.domain.Post;
import br.com.miausocial.core.imgs.app.ImgsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private final PostService service;
    private final ImgsService imgsService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Criar Post", description = "Criar Post.")
    @ApiResponse(responseCode = "201", description = "Sucesso", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<Void> cadastrar(@Parameter(
                                                  schema = @Schema(implementation = NewPost.class)
                                          ) @RequestPart(value = "cmd") @Validated String value,
                                          @RequestPart(value = "perfil", required = false) List<MultipartFile> imgs) throws Exception {

         NewPost cmd = objectMapper.readValue(new String(value.getBytes(), StandardCharsets.UTF_8), NewPost.class);
         cmd.addImgsUrls(imgsService.uploadImages(imgs));

         UUID id = service.handle(cmd);

        return ResponseEntity.created(fromCurrentRequest()
                        .path("/").path(id.toString()).build().toUri())
                .build();
    }

    @GetMapping
    @Operation(summary = "Listar Posts", description = "Lista todos os posts com paginação.")
    public ResponseEntity<Page<Post>> getAllPosts(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/all")
    @Operation(summary = "Listar Todos Posts", description = "Lista todos os posts sem paginação.")
    public ResponseEntity<List<Post>> getAllPostsWithoutPagination() {
        return ResponseEntity.ok(service.findAll());
    }
}
