package vttp.ssf.Day17Lecture.Service;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProcessService {


    String URL_BOOKSEARCH = "https://openlibrary.org/search.json?author=";
    String URL_COUNTRIES = "https://api.first.org/data/v1/countries";
    RestTemplate template = new RestTemplate(); // New
    
    public String searchBook(String author) {

        // Transferred from ProcessController.
        String updatedURL = URL_BOOKSEARCH + author;
        // URL_BOOKSEARCH += author;
        String result = template.getForObject(updatedURL, String.class); // **** Get for object returns you a String.
        return result;
    }

    public ResponseEntity<String> getCountries() {
        ResponseEntity<String> result = template.getForEntity(URL_COUNTRIES, String.class); // get for entity Returns you a response entity.
        return result;
    }

    public ResponseEntity<String> filterCountries(String name) {
        String updatedUrl = URL_COUNTRIES + "?q=" + name;
        ResponseEntity<String> result = template.getForEntity(updatedUrl, String.class);

        return result;
    }

    public ResponseEntity<String> filterCountriesbyRegion(String region) {
        String updatedUrl = URL_COUNTRIES + "?region=" + region;
        ResponseEntity<String> result = template.getForEntity(updatedUrl, String.class);

        return result;
    }

}
