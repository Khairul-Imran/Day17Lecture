package vttp.ssf.Day17Lecture.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.ssf.Day17Lecture.Service.ProcessService;

@RestController
@RequestMapping(path = "/process")
public class ProcessController {
    
    @Autowired
    ProcessService processService;


    @PostMapping(path = "/searchBook", produces = "application/json")
    public String processBookSearch(@RequestBody MultiValueMap<String, String> form) {
        String author = form.getFirst("seachName");
        System.out.printf("Author: %s\n", author);

        String result = processService.searchBook(author); // ****

        return result;
    }

    // After Lunch
    @PostMapping(path = "searchCountry")
    public String processCountrySearch(@RequestBody MultiValueMap<String, String> form) {

        ResponseEntity<String> results = processService.filterCountries(form.getFirst("searchName"));

        return results.getBody();
    }

    @PostMapping(path = "searchCountryByRegion")
    public String processCountrySearchByRegion(@RequestBody MultiValueMap<String, String> form) {

        ResponseEntity<String> results = processService.filterCountriesbyRegion(form.getFirst("searchRegion")); // This must be the same as the select tag's name in your html********

        return results.getBody();
    }

}
