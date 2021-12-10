package web.rest_template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import web.rest_template.model.User;

import java.util.Collections;

@SpringBootApplication
public class RestTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateApplication.class, args);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        final String URL = "http://91.241.64.178:7081/api/users/";

        String result = "";

        User user = new User();
        user.setAge((byte) 21);
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");

        User editUser = new User(3L, "Thomas", "Shelby", (byte) 21);

        //GET
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> res = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        System.out.println(res);
        String cookie = res.getHeaders().get("Set-Cookie").get(0);
        headers.set("Cookie", cookie);
        headers.set("Content-Type", "application/json");

        //POST
        HttpEntity<User> postEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> postRes = restTemplate.exchange(URL, HttpMethod.POST, postEntity, String.class);
        result += postRes.getBody();

        //PUT
        HttpEntity<User> putEntity = new HttpEntity<>(editUser, headers);
        ResponseEntity<String> putRes = restTemplate.exchange(URL, HttpMethod.PUT, putEntity, String.class);
        result += putRes.getBody();

        //DELETE
        HttpEntity<User> deleteEntity = new HttpEntity<>(headers);
        ResponseEntity<String> deleteRes = restTemplate.exchange(URL + "3", HttpMethod.DELETE,
                deleteEntity, String.class);
        result += deleteRes.getBody();

        System.out.println();
        System.out.println("Итоговый код:" + result);
    }

}
