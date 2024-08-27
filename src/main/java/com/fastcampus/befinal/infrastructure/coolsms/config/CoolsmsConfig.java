package com.fastcampus.befinal.infrastructure.coolsms.config;

import com.fastcampus.befinal.infrastructure.coolsms.template.CoolsmsTemplate;
import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoolsmsConfig {
    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.from.contact}")
    private String fromContact;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    @Bean
    public CoolsmsTemplate coolsmsTemplate() {
        return new CoolsmsTemplate(messageService, fromContact);
    }
}
