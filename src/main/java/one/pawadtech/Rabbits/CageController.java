package one.pawadtech.Rabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;

import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("/api/cages")
public class CageController {

    private static final Logger logger = LoggerFactory.getLogger(CageController.class);

    @Autowired
    private CageService cageService;

    @GetMapping
    public ResponseEntity<List<Cage>> getAllcages(){
        logger.info("Request received to get all cages."); // Log the entry point of the method
        System.out.println("ALL ::: "+ cageService.allCages());
        logger.info("ALL ::: {}", cageService.allCages()); // Log the cages retrieved
        return new ResponseEntity<List<Cage>>( cageService.allCages(), HttpStatus.OK);
    }

//    @GetMapping("/cageTag")
//    public ResponseEntity<List<Rabbit>> getAllRabbitsInCage(@PathVariable String cageTag){
//        logger.info("Request received to get all rabbits in cage with tag: {}", cageTag); // Log the entry point of the method
//        System.out.println("ALL ::: "+ cageService.getRabbitsInCage());
//        logger.info("Rabbits in cage {} ::: {}", cageTag, cageService.getRabbitsInCage(cageTag)); // Log the rabbits retrieved
//        return new ResponseEntity<List<Rabbit>>( cageService.getRabbitsInCage(cageTag), HttpStatus.OK);
//    }

}
