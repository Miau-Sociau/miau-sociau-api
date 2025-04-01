package br.com.miausocial.infra.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    byte[] getFileBytes(String key);
    String uploadFile(MultipartFile file);
}
