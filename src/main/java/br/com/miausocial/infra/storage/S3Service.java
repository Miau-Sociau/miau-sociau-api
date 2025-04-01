package br.com.miausocial.infra.storage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class S3Service implements StorageService {
    private final S3Client s3Client;
    private final String bucketName = "test-bucket";

    public S3Service() {
        this.s3Client = S3Client.builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    public String getFileUrl(String bucketName, String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
    }
    @Override
    public byte[] getFileBytes(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        return objectBytes.asByteArray();
    }


    public String uploadFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String key = "i/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

            return String.format("https://%s.s3.sa-east-1.amazonaws.com/%s", bucketName, key);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload para S3", e);
        }
    }
}
