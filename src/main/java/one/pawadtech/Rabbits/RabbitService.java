package one.pawadtech.Rabbits;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RabbitService {
    @Autowired
    private RabbitRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Rabbit> allRabbits(){
        return repository.findAll();
    }

    public Optional<Rabbit> findRabbitBySerialNum(String tagNum){ //findRabbitBySerialNo
        return repository.findRabbitBytagNo(tagNum);
    }

    public List<Rabbit> getRabbitsInCage(String cageNo){
        return repository.findByCage(cageNo);
    }

    //@Transactional
    public Rabbit createRabbit(Rabbit newRabbit) {
        // Save the rabbit to the Rabbit collection
        Rabbit savedRabbit = repository.save(newRabbit);

//        if (savedRabbit != null) {
//            // Update the corresponding cage with the new rabbit's tag number
//            String cageTag = savedRabbit.getCage();
//            Query query = new Query(Criteria.where("cageTag").is(cageTag));
//            Update update = new Update().push("rabbitsInCage", savedRabbit.getTagNo()); fnfn ngnnfnbbbb
//
//            // Execute the update operation
//            mongoTemplate.updateFirst(query, update, Cage.class);
//
//            // Optionally, you may want to update the rabbit's cage field
//            savedRabbit.setCage(cageTag);
//            repository.save(savedRabbit);
//        }

        return savedRabbit;
    }

//    public Rabbit createRabbit(Rabbit newRabbit){
//
//        Rabbit rabbit = repository.insert(new Rabbit(newRabbit));
//
//        mongoTemplate.update(Cage.class)
//                .matching(Criteria.where("cageTag").is(cage))
//                .apply(new Update().push("rabbitsInCage").value(rabbit.getTagNo()))
//                .first();
//        return rabbit;
//    }

}
