package br.com.miausocial.infra.imgs.app;

import br.com.miausocial.infra.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImgsService {

    private final StorageService storageService;

    public List<String> uploadImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) return List.of();

        return images.stream()
                .map(storageService::uploadFile)
                .collect(Collectors.toList());
    }

    public byte[] getImageBytes(String key) {
        return storageService.getFileBytes(key);
    }
}
