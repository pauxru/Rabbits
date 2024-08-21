package one.pawadtech.Rabbits;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(RabbitsController.class);

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
    public ResponseEntity<List<Rabbit>> getAllRabbits() {
        List<Rabbit> allRabbits = service.allRabbits();
        logger.info("ALL ::: " + allRabbits);
        System.out.println("ALL ::: " + allRabbits);
        return new ResponseEntity<>(allRabbits, HttpStatus.OK);
    }

    @GetMapping("/manageMatings")
    public ResponseEntity<String> manageMatingStuff() {
        String result = checkMatingActions.manageMatings();
        logger.info("Manage Matings ::: " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("getAll/{sex}")
    public ResponseEntity<List<Rabbit>> getAllSameSexRabbits(@PathVariable String sex) {
        List<Rabbit> sameSexRabbits = service.getSameSexRabbits(sex);
        logger.info("Get All Same Sex Rabbits for sex " + sex + " ::: " + sameSexRabbits);
        return new ResponseEntity<>(sameSexRabbits, HttpStatus.OK);
    }

    @GetMapping("/createNewMating")
    public ResponseEntity<List<String>> getToDoItems() {
        logger.info("Getting the to do items");
        System.out.println("Getting the to do items");

        ResponseEntity<List<Rabbit>> femaleResponseEntity = getAllSameSexRabbits("female");

        if (femaleResponseEntity.getStatusCode() == HttpStatus.OK) {
            List<Rabbit> femaleRabbits = femaleResponseEntity.getBody();
            Rabbit selectedFemaleMate = getMatingToDo.GetFemaleMate(femaleRabbits);
            if (selectedFemaleMate != null) {
                logger.info("Female Rabbit::: " + selectedFemaleMate.getTagNo());
                System.out.println("Female Rabbit::: " + selectedFemaleMate.getTagNo());

                ResponseEntity<List<Rabbit>> maleResponseEntity = getAllSameSexRabbits("male");
                if (maleResponseEntity.getStatusCode() == HttpStatus.OK) {
                    List<Rabbit> maleRabbits = maleResponseEntity.getBody();
                    Rabbit selectedMaleMate = getMatingToDo.selectMaleMate(selectedFemaleMate, maleRabbits);
                    if (selectedMaleMate != null) {
                        logger.info("Male Rabbit::: " + selectedMaleMate.getTagNo());
                        System.out.println("Male Rabbit::: " + selectedMaleMate.getTagNo());

                        try {
                            String newmating_Id = selectedMaleMate.getTagNo().substring(2, 5) + selectedFemaleMate.getTagNo().substring(2, 5);
                            matingService.addNewMatingRecord(newmating_Id, selectedMaleMate.getTagNo(), selectedFemaleMate.getTagNo(), null, false, false,  null, null, null, null, "", "", null, null, null, "");
                        } catch (Exception e) {
                            logger.error("Error inserting new mate session ::: " + e.getMessage(), e);
                            System.out.println("Error inserting new mate session ::: " + e);
                        }
                    } else {
                        logger.info("Found no eligible Male mate");
                        System.out.println("Found no eligible Male mate");
                    }
                } else {
                    logger.error("Error retrieving male rabbits");
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                logger.info("Found no eligible female mate");
                System.out.println("Found no eligible female mate");
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.error("Error retrieving female rabbits");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cage/{cageNo}")
    public List<Rabbit> getRabbitsInCage(@PathVariable String cageNo) {
        List<Rabbit> rabbitsInCage = service.getRabbitsInCage(cageNo);
        logger.info("Rabbits in Cage " + cageNo + " ::: " + rabbitsInCage);
        return rabbitsInCage;
    }

    @GetMapping("/{tagNo}")
    public ResponseEntity<Optional<Rabbit>> getSingleRabbit(@PathVariable String tagNo) {
        Optional<Rabbit> rabbit = service.findRabbitBySerialNum(tagNo);
        logger.info("BY TAG ::: " + rabbit);
        System.out.println("BY TAG ::: " + rabbit);
        return new ResponseEntity<>(rabbit, HttpStatus.OK);
    }

    @PostMapping(value = "/create_rabbit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rabbit> createRabbit(@RequestBody Map<String, String> payload) {
        logger.info("Create Rabbit ::: " + payload);
        System.out.println("Create Rabbit ::: " + payload);

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
        try {
            return java.sql.Date.valueOf(LocalDate.parse(dateString));
        } catch (DateTimeParseException e) {
            logger.error("Error parsing date string: " + dateString, e);
            return null;
        }
    }

    private List<String> parseImages(String imagesString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(imagesString, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            logger.error("Error parsing images string: " + imagesString, e);
            return Collections.emptyList(); // or another appropriate default value
        }
    }
}

@Component
class GetMatingToDo {

    private static final Logger logger = LoggerFactory.getLogger(GetMatingToDo.class);

    @Autowired
    private static DateService dateService = null;

    private static MatingService matingService = null;

    @Autowired
    public GetMatingToDo(MatingService matingService) {
        this.matingService = matingService;
    }

    public Rabbit GetFemaleMate(List<Rabbit> rabbits) {
        logger.info("Here to get female mate");
        System.out.println("Here to get female mate");

        List<Rabbit> processedRabbits = new ArrayList<>();
        for (Rabbit rabbit : rabbits) {
            logger.info("PROCESSING ::: " + rabbit.getTagNo());
            System.out.println("PROCESSING ::: " + rabbit.getTagNo());

            Optional<Mating> mating = matingService.findLatestMatingByFemale(rabbit.getTagNo().toString());
            if (mating.isPresent()) {
                Mating mate = mating.get();
                logger.info("Tag NO ::: " + rabbit.getTagNo() + " -> Mating ID::: " + mate.getMatingNum());
                System.out.println("Tag NO ::: " + rabbit.getTagNo() + " -> Mating ID::: " + mate.getMatingNum());

                long matingDuration = 0;
                if (Optional.ofNullable(mate.getMate_to()).isPresent()) {
                    matingDuration = dateService.durationFrom(mate.getMate_to()).toDays();
                }
                logger.info(rabbit.getTagNo() + " >> Mating duration :: " + matingDuration);
                System.out.println(rabbit.getTagNo() + " >> Mating duration :: " + matingDuration);
                if (matingDuration < 14) {
                    logger.info(rabbit.getTagNo() + " : Female served recently");
                    System.out.println(rabbit.getTagNo() + " : Female served recently");
                    continue;
                } else {
                    if (!mate.getPregnancy_confirmed().toString().isEmpty()) {
                        if (mate.getPregnancy_confirmed().toString().equalsIgnoreCase("1")) {
                            logger.info(rabbit.getTagNo() + " : Female pregnant");
                            System.out.println(rabbit.getTagNo() + " : Female pregnant");
                            continue;
                        }
                    } else {
                        logger.info(rabbit.getTagNo() + " : Pregnancy not confirmed yet");
                        System.out.println(rabbit.getTagNo() + " : Pregnancy not confirmed yet");
                        continue;
                    }
                    logger.info(rabbit.getTagNo() + " : Not served recently but failed. check it out");
                    System.out.println(rabbit.getTagNo() + " : Not served recently but failed. check it out");
                }
            } else {
                logger.info("Tag NO ::: " + rabbit.getTagNo() + " -> No mating History");
                System.out.println("Tag NO ::: " + rabbit.getTagNo() + " -> No mating History");
            }

            try {
                if (dateService.StrdurationFrom(rabbit.getBirthday()).toDays() < 15) {
                    continue;
                }
            } catch (DateTimeParseException e) {
                logger.error("Error parsing date: " + e.getMessage(), e);
                System.err.println("Error parsing date: " + e.getMessage());
                continue;
            } catch (NullPointerException ee) {
                logger.error("Error parsing date: " + ee.getMessage(), ee);
                System.err.println("Error parsing date: " + ee.getMessage());
                continue;
            }

            processedRabbits.add(rabbit);
        }

        Rabbit OneSelected = null;
        String oldestTag = "";
        if (processedRabbits.size() > 1) {
            long OldestAge = 0;
            for (Rabbit selectedRabbit : processedRabbits) {
                long currRabbitAge = dateService.StrdurationFrom(selectedRabbit.getBirthday()).toDays();
                if (currRabbitAge > OldestAge) {
                    OldestAge = currRabbitAge;
                    oldestTag = selectedRabbit.getTagNo().toString();
                    OneSelected = selectedRabbit;
                }
            }
        } else if (processedRabbits.size() == 1) {
            oldestTag = processedRabbits.get(0).getTagNo().toString();
            OneSelected = processedRabbits.get(0);
        }

        return OneSelected;
    }

    public static Rabbit selectMaleMate(Rabbit femaleMate, List<Rabbit> rabbits) {
        List<Rabbit> processedMaleRabbits = new ArrayList<>();
        for (Rabbit rabbit : rabbits) {
            if (femaleMate.getMother().toString().equalsIgnoreCase(rabbit.getMother().toString())) {
                logger.info("Same Mother for " + femaleMate.getTagNo().toString() + " and " + rabbit.getTagNo().toString());
                System.out.println("Same Mother for " + femaleMate.getTagNo().toString() + " and " + rabbit.getTagNo().toString());
                continue;
            }
            if (femaleMate.getFather().toString().equalsIgnoreCase(rabbit.getFather().toString())) {
                logger.info("Same Father for " + femaleMate.getTagNo().toString() + " and " + rabbit.getTagNo().toString());
                System.out.println("Same Father for " + femaleMate.getTagNo().toString() + " and " + rabbit.getTagNo().toString());
                continue;
            }
            try {
                if (dateService.StrdurationFrom(rabbit.getBirthday()).toDays() < 15) {
                    continue;
                }
            } catch (DateTimeParseException e) {
                logger.error("Error parsing date: " + e.getMessage(), e);
                System.err.println("Error parsing date: " + e.getMessage());
                continue;
            } catch (NullPointerException ee) {
                logger.error("Error parsing date: " + ee.getMessage(), ee);
                System.err.println("Error parsing date: " + ee.getMessage());
                continue;
            }

            Optional<Mating> mating = matingService.findLatestMatingByMale(rabbit.getTagNo().toString());
            if (mating.isPresent()) {
                Mating mate = mating.get();
                logger.info("Tag NO ::: " + rabbit.getTagNo() + " -> Mating ID::: " + mate.getMatingNum());
                System.out.println("Tag NO ::: " + rabbit.getTagNo() + " -> Mating ID::: " + mate.getMatingNum());

                long maleLastServe = 0;
                if (Optional.ofNullable(mate.getMate_to()).isPresent()) {
                    maleLastServe = dateService.durationFrom(mate.getMate_to()).toDays();
                }
                if (maleLastServe < 5) {
                    logger.info("Male served recently");
                    System.out.println("Male served recently");
                    continue;
                }
            } else {
                logger.info("Tag NO ::: " + rabbit.getTagNo() + " -> No mating History");
                System.out.println("Tag NO ::: " + rabbit.getTagNo() + " -> No mating History");
            }
            processedMaleRabbits.add(rabbit);
        }

        logger.info("processedMaleRabbits ::: " + processedMaleRabbits);
        System.out.println("processedMaleRabbits ::: " + processedMaleRabbits);

        Rabbit OneSelected = null;
        String oldestTag = "";
        if (processedMaleRabbits.size() > 1) {
            long OldestAge = 0;
            for (Rabbit selectedRabbit : processedMaleRabbits) {
                long currRabbitAge = dateService.StrdurationFrom(selectedRabbit.getBirthday()).toDays();
                if (currRabbitAge > OldestAge) {
                    OldestAge = currRabbitAge;
                    oldestTag = selectedRabbit.getTagNo().toString();
                    OneSelected = selectedRabbit;
                }
            }
        } else if (processedMaleRabbits.size() == 1) {
            oldestTag = processedMaleRabbits.get(0).getTagNo().toString();
            OneSelected = processedMaleRabbits.get(0);
        }

        return OneSelected;
    }
}
