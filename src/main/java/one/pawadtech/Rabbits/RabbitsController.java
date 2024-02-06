package one.pawadtech.Rabbits;

import org.bson.types.ObjectId;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/api/rabbits")
public class RabbitsController {
    @Autowired
    private RabbitService service;
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Rabbit>> getAllRabbits(){
        System.out.println("ALL ::: "+ service.allRabbits());
        return new ResponseEntity<List<Rabbit>>( service.allRabbits(), HttpStatus.OK);
    }

    @GetMapping("/get/toDo")
    public ResponseEntity<List<String>> getToDoItems(){
        System.out.println("Getting the to do items");
        // Call the getAllRabbits method to retrieve the list of rabbits
        ResponseEntity<List<Rabbit>> responseEntity = getAllRabbits();

        // Check if the response is successful
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Get the list of rabbits from the response body
            List<Rabbit> rabbits = responseEntity.getBody();

            // Process the list of rabbits (for example, convert them to strings)
            List<String> processedRabbits = new ArrayList<>();
            for (Rabbit rabbit : rabbits) {
                String processedInfo = "ID: " + rabbit.getTagNo() +
                                        "DOB: " + rabbit.getBirthday();
                processedRabbits.add(processedInfo);
                System.out.println("Rabbit::: " + rabbit.getTagNo());
            }

            // Return the processed rabbits as a response
            return new ResponseEntity<>(processedRabbits, HttpStatus.OK);
        } else {
            // If the response is not successful, return an appropriate error response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/cage/{cageNo}")
    public List<Rabbit> getRabbitsInCage(@PathVariable String cageNo){
        return service.getRabbitsInCage(cageNo);
    }
    @GetMapping("/{tagNo}")
    public ResponseEntity<Optional<Rabbit>> getSingleRabbit(@PathVariable String tagNo){
        System.out.println("BY TAG ::: "+ service.findRabbitBySerialNum(tagNo));
        return  new ResponseEntity<Optional<Rabbit>>(service.findRabbitBySerialNum(tagNo), HttpStatus.OK);
    }

    @PostMapping(value = "/create_rabbit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rabbit> createRabbit(@RequestBody Map<String, String> payload){
        System.out.println("Create Rabbit ::: ");
        Rabbit newRabbit = new Rabbit(
                payload.get("tagNo"),
                payload.get("present"),
                parseDateString(payload.get("birthday")),
                payload.get("breed"),
                payload.get("mother"),
                payload.get("father"),
                payload.get("sex"),
                payload.get("origin"),
                payload.get("diseases"),
                payload.get("comments"),
                payload.get("weight"),
                payload.get("price_sold"),
                payload.get("cage"),
                parseImages(payload.get("images"))
        );
        Rabbit createdRabbit = service.createRabbit(newRabbit);
        return new ResponseEntity<>(createdRabbit, HttpStatus.CREATED);

    }
    private Date parseDateString(String dateString) {
        // Implement the logic to parse the string to a Date
        // For example, use java.time.LocalDate
        try {
            return java.sql.Date.valueOf(LocalDate.parse(dateString));
        } catch (DateTimeParseException e) {
            // Handle parsing exception appropriately
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to parse a string representing a list of images
    private List<String> parseImages(String imagesString) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Attempt to deserialize the input string as a list of strings
            return objectMapper.readValue(imagesString, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            // Handle the exception appropriately (e.g., log it, return a default value)
            e.printStackTrace();
            return Collections.emptyList(); // or another appropriate default value
        }
    }

}
