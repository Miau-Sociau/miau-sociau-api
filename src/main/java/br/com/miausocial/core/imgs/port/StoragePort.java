package br.com.miausocial.core.imgs.port;

import org.springframework.web.multipart.MultipartFile;

public interface StoragePort {
    byte[] getFileBytes(String key);
    String uploadFile(MultipartFile file);
}