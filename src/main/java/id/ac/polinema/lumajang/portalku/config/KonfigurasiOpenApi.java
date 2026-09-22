package id.ac.polinema.lumajang.portalku.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KonfigurasiOpenApi {
    @Bean
    public OpenAPI portalkuOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Portalku API")
                .version("v1")
                .description("API portal informasi akademik - latihan MK Pemrograman Web Lanjut, "
                        + "D3 Teknologi Informasi PSDKU Lumajang")
                .contact(new Contact().name("D3 TI PSDKU Lumajang")));
    }
}