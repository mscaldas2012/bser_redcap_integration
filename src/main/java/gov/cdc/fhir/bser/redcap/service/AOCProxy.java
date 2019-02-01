package gov.cdc.fhir.bser.redcap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class AOCProxy {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${BSER_SENDER.URL}")
    private String rootURL;

    public ResponseEntity<String> sendFeedback(String input) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity<String> requestEntity = new HttpEntity<>(input, headers);
        return restTemplate.postForEntity(rootURL, requestEntity, String.class);
       // return null;
    }
}
