package com.sendoa.opendata.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class HealthOpenData implements HealthIndicator {

    @Autowired
    private Config config;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Health health() {
        int errorCode = checkOpendata();
        if (errorCode != HttpStatus.OK.value()) {
            return Health.down().withDetail("error.code", errorCode).build();
        }
        return Health.up().build();
    }

    private int checkOpendata() {
        final URI uri = UriComponentsBuilder.fromUriString(config.getHost())
                .build().normalize().toUri();

        final HttpEntity httpEntity = new HttpEntity(new HttpHeaders());
        final ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.OPTIONS, httpEntity, String.class);

        return response.getStatusCodeValue();
    }
}

