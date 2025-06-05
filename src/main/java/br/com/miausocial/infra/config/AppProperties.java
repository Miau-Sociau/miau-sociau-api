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
    private Security security = new Security();
    private Storage storage = new Storage();
    private Cors cors = new Cors();

    @Getter
    @Setter
    public static class Security {
        private String jwtSecret;
        private long jwtExpirationMs = 86400000; // 24 hours
        private long jwtRefreshExpirationMs = 604800000; // 7 days
    }

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
    public static class Cors {
        private String[] allowedOrigins = {"http://localhost:4321"};
        private String[] allowedMethods = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
        private String[] allowedHeaders = {"*"};
    }
} 