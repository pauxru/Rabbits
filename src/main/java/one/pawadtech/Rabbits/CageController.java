package one.pawadtech.Rabbits;

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

    @Autowired
    private CageService cageService;

    @GetMapping
    public ResponseEntity<List<Cage>> getAllcages(){
        System.out.println("ALL ::: "+ cageService.allCages());
        return new ResponseEntity<List<Cage>>( cageService.allCages(), HttpStatus.OK);
    }

//    @GetMapping("/cageTag")
//    public ResponseEntity<List<Rabbit>> getAllRabbitsInCage(@PathVariable String cageTag){
//        //System.out.println("ALL ::: "+ cageService.getRabbitsInCage());
//        return new ResponseEntity<List<Rabbit>>( cageService.getRabbitsInCage(cageTag), HttpStatus.OK);
//    }

}
