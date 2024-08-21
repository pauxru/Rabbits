package one.pawadtech.Rabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CageService {

    private static final Logger logger = LoggerFactory.getLogger(CageService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CagesRepository repository;

    public List<Cage> allCages(){
        logger.info("Fetching all cages from the repository."); // Log the method entry
        List<Cage> cages = repository.findAll();
        logger.info("Fetched {} cages from the repository.", cages.size()); // Log the result
        return cages;
    }

//    public List<Rabbit> getRabbitsInCage(String cageNo){
//        logger.info("Fetching rabbits in cage with number: {}", cageNo); // Log the method entry
//        System.out.println("ALL ::: Here at rabbits in cage");
//        logger.info("Here at rabbits in cage"); // Log alongside println
//        LookupOperation lookOperation = LookupOperation.newLookup()
//                .from("cages")
//                .localField("cageTag")
//                .foreignField("_id")
//                .as("CiRR");
//
//        MatchOperation matchOperation = Aggregation.match(new Criteria());
//        Aggregation aggregation = Aggregation.newAggregation(
//                lookOperation,
//                matchOperation
//        );
//
//        List<Rabbit> rabbits = mongoTemplate.aggregate(aggregation, "Rabbits", Rabbit.class).getMappedResults();
//        logger.info("Fetched {} rabbits in the specified cage.", rabbits.size()); // Log the result
//        return rabbits;
//    }

//    public List<Rabbit> getRabbitsInCage(String cageNo) {
//        logger.info("Fetching rabbits in cage with number: {}", cageNo); // Log the method entry
//        System.out.println("ALL ::: Here at rabbits in cage");
//        logger.info("Here at rabbits in cage"); // Log alongside println
//
//        LookupOperation lookupOperation = LookupOperation.newLookup()
//                .from("cages")
//                .localField("cageTag")
//                .foreignField("_id")
//                .as("cageInfo");
//
//        MatchOperation matchOperation = Aggregation.match(Criteria.where("cage").is(cageNo));
//
//        Aggregation aggregation = Aggregation.newAggregation(
//                lookupOperation,
//                matchOperation
//        );
//
//        List<Rabbit> rabbits = mongoTemplate.aggregate(aggregation, "Rabbits", Rabbit.class).getMappedResults();
//        logger.info("Fetched {} rabbits in the specified cage.", rabbits.size()); // Log the result
//        return rabbits;
//    }
}
