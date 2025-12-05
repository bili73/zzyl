package com.zzyl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "zzyl.framework.security")
public class SecurityProperties {

    private List<String> publicAccessUrls;

}