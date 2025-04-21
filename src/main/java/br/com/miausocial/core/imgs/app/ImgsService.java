package br.com.miausocial.core.imgs.app;

import br.com.miausocial.core.imgs.port.StoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImgsService {

    private final StoragePort storage;
    public List<String> uploadImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) return List.of();

        return images.stream()
                .map(storage::uploadFile)
                .collect(Collectors.toList());
    }

    public byte[] getImageBytes(String key) {
        return storage.getFileBytes(key);
    }
}
