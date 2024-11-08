package ru.ssau.blazebankapigateway.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.ssau.blazebankapigateway.model.Credential;

@Component
public class AuthUtil {

    @Autowired
    private RestTemplate restTemplate;

    public String getToken(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", username);
        headers.set("password", password);
        HttpEntity<Credential> request = new HttpEntity<>(new Credential("admin", "admin"), headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8088/login", HttpMethod.POST,request,String.class); //TODO: поправить на кластерный Security сервис
        System.out.println("token:"+response.getBody());
        return response.getBody();
    }
}