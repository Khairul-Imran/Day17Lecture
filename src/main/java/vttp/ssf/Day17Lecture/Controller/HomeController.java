package vttp.ssf.Day17Lecture.Controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.ssf.Day17Lecture.Model.Country;
import vttp.ssf.Day17Lecture.Service.ProcessService;

@Controller
@RequestMapping(path = "/home")
public class HomeController {

    @Autowired
    ProcessService processService;

    @GetMapping(path = "/booksearch", produces = "application/json") // By default it is json.
    public String bookSearchForm() {
        return "booksearch";
    }

    @GetMapping(path = "/countries")
    public ResponseEntity<String> listCountries() { //***
        ResponseEntity<String> result = processService.getCountries();
        
        return result;
    }

    // This is the copy of the above.
    @GetMapping(path = "/countries/jsontest") // Showing how we can process the data using json to read the string of data. -> Imported the Json P dependency.
    public String listCountries2(Model model) { //*** -> returns the status code along with the payload data ************** // Added model for thymeleaf template.
        ResponseEntity<String> result = processService.getCountries();
        
        String jsonString = result.getBody().toString(); // ***** -> removes the status code. If don't remove, got error.
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        // Use the reader to convert it to a json object.
        JsonObject jsonObject = jsonReader.readObject();

        // System.out.println("jsonObject: " + jsonObject);

        // After lunch.
        JsonObject jsonObjectData = jsonObject.getJsonObject("data");
        System.out.println("jsonObjectData: " + jsonObjectData);
        System.out.println("jsonObjectData size: " + jsonObjectData.size());
        // These will contain the key - values of the data - How???

        // Creating an empty list, to populate the objects we get further below.
        List<Country> countries = new ArrayList<Country>();

        Set<Entry<String, JsonValue>> entries = jsonObjectData.entrySet();
        for (Entry<String, JsonValue> entry : entries) {
            // System.out.println(entry.getKey() + ">" + entry.getValue().toString()); -> compare what you get from this and the one below
            System.out.println(entry.getKey() + ">" + entry.getValue().asJsonObject().getString("country")); // Prints the key-values (value includes the country name and region) in your terminal.
            
            countries.add(new Country(entry.getKey(), entry.getValue().asJsonObject().getString("country")));
        } 

        // Added this for the thymeleaf template. The name we used is important!!
        model.addAttribute("countries", countries);

        // return result; --> commented out after he changed to return to string (used to be ResponseEntity)
        return "countrylist";

        // Comment from just after lunch!
        // Ultimate goal is to read the data and put them into an array list??******* 
        // String -> Json objects -> store into entity objects
    }

    @GetMapping(path = "/countrysearch")
    public String countrySearchForm() {

        return "countrysearch";
    }

    @GetMapping(path = "/countrysearchbyregion")
    public String countrySearchByRegionForm(Model model) {
        // Similar to above.
        ResponseEntity<String> result = processService.getCountries();
        String jsonString = result.getBody().toString();
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        // Use the reader to convert it to a json object.
        JsonObject jsonObject = jsonReader.readObject();

        JsonObject jsonObjectData = jsonObject.getJsonObject("data");

        Set<String> regions = new HashSet<>();
        Set<Entry<String, JsonValue>> entries = jsonObjectData.entrySet();

        for (Entry<String, JsonValue> entry : entries) {
            regions.add(entry.getValue().asJsonObject().getString("region"));
        }

        model.addAttribute("regions", regions); // This is the same regions that appears in the html template.

        return "countrysearchbyregion";
    }
    
}
