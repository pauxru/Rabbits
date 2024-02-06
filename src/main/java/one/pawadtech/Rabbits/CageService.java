package one.pawadtech.Rabbits;

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
//    @Autowired
//    private RabbitRepository rabbitRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CagesRepository repository;
    public List<Cage> allCages(){
        return repository.findAll();
    }

//    public List<Rabbit> getRabbitsInCage(String cageNo){
//        System.out.println("ALL ::: Here at rabbits in cage");
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
//        return mongoTemplate.aggregate(aggregation, "Rabbits", Rabbit.class).getMappedResults();
//    }

//    public List<Rabbit> getRabbitsInCage(String cageNo) {
//        System.out.println("ALL ::: Here at rabbits in cage");
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
//        return mongoTemplate.aggregate(aggregation, "Rabbits", Rabbit.class).getMappedResults();
//    }


}
