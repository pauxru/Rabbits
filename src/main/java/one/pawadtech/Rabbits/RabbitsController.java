package one.pawadtech.Rabbits;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.*;


@RestController
@RequestMapping("/api/rabbits")
public class RabbitsController {

    LocalDate Today = LocalDate.now();
    @Autowired
    private RabbitService service;


    private CheckMatingActions checkMatingActions;

    public RabbitsController(CheckMatingActions checkMatingActions) {
        this.checkMatingActions = checkMatingActions;
    }
    @Autowired
    private MatingService matingService;

    @Autowired
    private GetMatingToDo getMatingToDo;  // Inject GetMatingToDo
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Rabbit>> getAllRabbits(){
        System.out.println("ALL ::: "+ service.allRabbits());
        return new ResponseEntity<List<Rabbit>>( service.allRabbits(), HttpStatus.OK);
    }

    @GetMapping("/manageMatings")
    public ResponseEntity<String> manageMatingStuff(){
        return new ResponseEntity<String>(checkMatingActions.manageMatings(), HttpStatus.OK);

    }

    @GetMapping("getAll/{sex}")
    public ResponseEntity<List<Rabbit>> getAllSameSexRabbits(@PathVariable String sex){
        return new ResponseEntity<List<Rabbit>>( service.getSameSexRabbits(sex), HttpStatus.OK);

    }


    @GetMapping("/createNewMating")
    public ResponseEntity<List<String>> getToDoItems(){
        System.out.println("Getting the to do items");
        // Call the getAllRabbits method to retrieve the list of rabbits
        ResponseEntity<List<Rabbit>> femaleResponseEntity = getAllSameSexRabbits("female");


        // Check if the response is successful
        if (femaleResponseEntity.getStatusCode() == HttpStatus.OK) {
            // Get the list of rabbits from the response body
            List<Rabbit> femaleRabbits = femaleResponseEntity.getBody();
            //GetMatingToDo ToDo = new GetMatingToDo();
            Rabbit selectedFemaleMate = getMatingToDo.GetFemaleMate(femaleRabbits);
            if(selectedFemaleMate != null) {
                System.out.println("Female Rabbit::: " + selectedFemaleMate.getTagNo());

                ResponseEntity<List<Rabbit>> maleResponseEntity = getAllSameSexRabbits("male");
                if (maleResponseEntity.getStatusCode() == HttpStatus.OK){
                    List<Rabbit> maleRabbits = maleResponseEntity.getBody();
                    Rabbit selectedMaleMate = getMatingToDo.selectMaleMate(selectedFemaleMate, maleRabbits);
                    if (selectedMaleMate != null) {
                        System.out.println("Male Rabbit::: " + selectedMaleMate.getTagNo());

                        try {
                            String newmating_Id = selectedMaleMate.getTagNo().substring(2, 5) + selectedFemaleMate.getTagNo().substring(2, 5);
                            matingService.addNewMatingRecord(newmating_Id, selectedMaleMate.getTagNo(), selectedFemaleMate.getTagNo(), null, false, false,  null, null, null, null, "", "", null, null, null, "");

                        } catch (Exception e) {
                            System.out.println("Error inserting new mate session ::: " + e);
                        }
                    } else {
                        System.out.println("Found no eleigible Male mate");
                    }
                }else {
                    // If the response is not successful, return an appropriate error response
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else {
                System.out.println("Found no eleigible female mate");
            }

            String MatingInitialTask = "";

            // Return the processed rabbits as a response
            return new ResponseEntity<>(HttpStatus.OK);
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
        System.out.println("Create Rabbit ::: "+ payload);
        Rabbit newRabbit = new Rabbit(
                payload.get("tagNo"),
                payload.get("present"),
                payload.get("birthday"),
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

@Component
class GetMatingToDo{

    @Autowired
    private static DateService dateService=null;

    private static MatingService matingService = null;
    @Autowired
    public GetMatingToDo(MatingService matingService) {
        this.matingService = matingService;
    }
    public Rabbit GetFemaleMate(List<Rabbit> rabbits) {
        System.out.println("Here to get female mate");

        // Process the list of rabbits (for example, convert them to strings)
        List<Rabbit> processedRabbits = new ArrayList<>();
        for (Rabbit rabbit : rabbits) {

            System.out.println("PROCESSING ::: "+rabbit.getTagNo());
            //List<Mating> mating = matingService.findAllMating();
            Optional<Mating> mating = matingService.findLatestMatingByFemale(rabbit.getTagNo().toString());

            if (mating.isPresent()) {
                Mating mate = mating.get();
                System.out.println("Tag NO ::: "+rabbit.getTagNo()+" -> Mating ID::: "+ mate.getMatingNum());


                long matingDuration = 0;
                if(Optional.ofNullable(mate.getMate_to()).isPresent()){
                    matingDuration = dateService.durationFrom(mate.getMate_to()).toDays();
                }
                System.out.println(rabbit.getTagNo()+" >> Mating duration :: "+ matingDuration);
                if(matingDuration < 14){
                    System.out.println(rabbit.getTagNo()+" : Female served recently");
                    continue;
                }else {
                    if(!mate.getPregnancy_confirmed().toString().isEmpty()) {
                        if (mate.getPregnancy_confirmed().toString().equalsIgnoreCase("1")) {
                            System.out.println(rabbit.getTagNo() + " : Female pregnant");
                            continue;
                        }
                    }else{
                        System.out.println(rabbit.getTagNo()+" : Pregnancy not confirmed yet");
                        continue;
                    }
                    System.out.println(rabbit.getTagNo()+" : Not served recently but failed. check it out");
                }
            }else{
                System.out.println("Tag NO ::: "+rabbit.getTagNo()+" -> No mating History");
            }

            // Must be of age
            try {
                if(dateService.StrdurationFrom(rabbit.getBirthday()).toDays() < 15){
                    continue;
                }

            } catch (DateTimeParseException e) {
                // Handle the parsing exception
                System.err.println("Error parsing date: " + e.getMessage());
                continue;
            } catch (NullPointerException ee){
                System.err.println("Error parsing date: " + ee.getMessage());
                continue;
            }

            processedRabbits.add(rabbit);

        }
        Rabbit OneSelected = null;
        String oldestTag = "";
        if(processedRabbits.size() > 1) {
            long OldestAge = 0;
            for (Rabbit selectedRabbit : processedRabbits) {
                long currRabbitAge = dateService.StrdurationFrom(selectedRabbit.getBirthday()).toDays();
                if(currRabbitAge > OldestAge){
                    OldestAge = currRabbitAge;
                    oldestTag = selectedRabbit.getTagNo().toString();
                    OneSelected = selectedRabbit;
                }
            }
        }else if(processedRabbits.size() == 1) {
            oldestTag = processedRabbits.get(0).getTagNo().toString();
            OneSelected = processedRabbits.get(0);
        }

        return OneSelected;
    }

    public static Rabbit selectMaleMate(Rabbit femaleMate, List<Rabbit> rabbits){
        List<Rabbit> processedMaleRabbits = new ArrayList<>();
        for (Rabbit rabbit : rabbits) {

            // Must not be rated to the female
            if(femaleMate.getMother().toString().equalsIgnoreCase(rabbit.getMother().toString())){
                System.out.println("Same Mother for "+ femaleMate.getTagNo().toString()+ " and "+ rabbit.getTagNo().toString());
                continue;
            }
            if(femaleMate.getFather().toString().equalsIgnoreCase(rabbit.getFather().toString())){
                System.out.println("Same Father for "+ femaleMate.getTagNo().toString()+ " and "+ rabbit.getTagNo().toString());
                continue;
            }
            // Must be of age
            try {
                if(dateService.StrdurationFrom(rabbit.getBirthday()).toDays() < 15){
                    continue;
                }

            } catch (DateTimeParseException e) {
                // Handle the parsing exception
                System.err.println("Error parsing date: " + e.getMessage());
                continue;
            } catch (NullPointerException ee){
                System.err.println("Error parsing date: " + ee.getMessage());
                continue;
            }
            //List<Mating> mating = matingService.findAllMating();
            Optional<Mating> mating = matingService.findLatestMatingByMale(rabbit.getTagNo().toString());

            if (mating.isPresent()) {
                Mating mate = mating.get();
                System.out.println("Tag NO ::: "+rabbit.getTagNo()+" -> Mating ID::: "+ mate.getMatingNum());

                long maleLastServe = 0;
                if(Optional.ofNullable(mate.getMate_to()).isPresent()){
                    maleLastServe = dateService.durationFrom(mate.getMate_to()).toDays();
                }
                if(maleLastServe < 5){
                    System.out.println("Male served recently");
                    continue;
                }
            }else{
                System.out.println("Tag NO ::: "+rabbit.getTagNo()+" -> No mating History");
            }
            processedMaleRabbits.add(rabbit);
        }
        System.out.println("processedMaleRabbits ::: "+processedMaleRabbits);
        Rabbit OneSelected = null;
        String oldestTag = "";
        if(processedMaleRabbits.size() > 1) {
            long OldestAge = 0;
            for (Rabbit selectedRabbit : processedMaleRabbits) {
                long currRabbitAge = dateService.StrdurationFrom(selectedRabbit.getBirthday()).toDays();
                if(currRabbitAge > OldestAge){
                    OldestAge = currRabbitAge;
                    oldestTag = selectedRabbit.getTagNo().toString();
                    OneSelected = selectedRabbit;
                }
            }
        }else if(processedMaleRabbits.size() == 1) {
            oldestTag = processedMaleRabbits.get(0).getTagNo().toString();
            OneSelected = processedMaleRabbits.get(0);
        }

        return OneSelected;

    }




}

