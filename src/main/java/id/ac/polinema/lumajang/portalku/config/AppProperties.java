package id.ac.polinema.lumajang.portalku.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter

public class AppProperties {
    private String nama;
    private String versi;
    private int maksItem; // terhubung ke app.maks-item
    private String pesanSambutan; // terhubung ke app.pesan-sambutan
}
