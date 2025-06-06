package br.com.miausocial.infra.storage;

import br.com.miausocial.core.imgs.port.StoragePort;
import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Profile("dev")
public class InMemoryService implements StoragePort {

    private final Map<String, byte[]> storage = new ConcurrentHashMap<>();

    @Override
    public byte[] getFileBytes(String key) {
        byte[] bytes = storage.get(key);
        if (bytes == null) {
            throw new RuntimeException("Arquivo não encontrado com a chave: " + key);
        }
        return bytes;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String key = UUID.randomUUID().toString();
        try {
            storage.put(key, file.getBytes());
            return key;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao ler bytes do arquivo", e);
        }
    }
}
