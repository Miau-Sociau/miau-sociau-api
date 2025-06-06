package br.com.miausocial.infra.storage;

import br.com.miausocial.core.imgs.port.StoragePort;
import br.com.miausocial.infra.config.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.UUID;

@Service
@Profile("prod")
@RequiredArgsConstructor
public class S3Service implements StoragePort {
    private final AppProperties appProperties;
    private final S3Client s3Client;

    public String getFileUrl(String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", appProperties.getStorage().getBucketName(), key);
    }

    @Override
    public byte[] getFileBytes(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(appProperties.getStorage().getBucketName())
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        return objectBytes.asByteArray();
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String key = "i/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(appProperties.getStorage().getBucketName())
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

            return getFileUrl(key);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload para S3", e);
        }
    }
}
