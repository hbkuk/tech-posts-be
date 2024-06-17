package com.techbloghub.common.config;

import com.techbloghub.core.authentication.application.KakaoAuthProperties;
import com.techbloghub.core.authentication.application.KakaoUserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {KakaoAuthProperties.class, KakaoUserProperties.class})
public class ConfigurationConfig {
}
