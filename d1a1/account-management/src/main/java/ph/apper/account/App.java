package ph.apper.account;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public RestTemplate restTemplate() { return new RestTemplate(); }
    @RestController
    @RequestMapping("account")
    public static class AccountController {

        private final RestTemplate restTemplate;

        public AccountController(RestTemplate restTemplate){
            this.restTemplate = restTemplate;
        }

        @PostMapping
        public ResponseEntity register(@RequestBody CreateAccountRequest request){
            System.out.println(request);

            Activity activity = new Activity();
            activity.setAction("REGISTRATION");
            activity.setIdentifier("email="+request.getEmail());

            ResponseEntity<Object> response =
                    restTemplate.postForEntity("http://localhost:8081/activity", activity, Object.class);

            if (response.getStatusCode().is2xxSuccessful()){
                System.out.println("Successs");
            } else {
                System.out.println("Err: " + response.getStatusCode());
            }

            return ResponseEntity.ok().build();
        }


    }

    @Data
    public static class Activity{
        private String action;
        private String identifier;

    }
    @Data
    public static class CreateAccountRequest {
        private String firstName;
        private String lastName;
        private String Email;
        private String Password;
    }
}
