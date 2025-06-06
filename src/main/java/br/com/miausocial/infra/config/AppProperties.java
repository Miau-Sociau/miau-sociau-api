package br.com.miausocial.infra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Storage storage = new Storage();
    private Security security = new Security();

    @Getter
    @Setter
    public static class Storage {
        private String bucketName;
        private String region;
        private String accessKey;
        private String secretKey;
    }

    @Getter
    @Setter
    public static class Security {
        private String jwtSecret;
        private long jwtExpirationMs;
    }
} 