package one.pawadtech.Rabbits;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RabbitService {
    private static final Logger logger = LoggerFactory.getLogger(RabbitService.class);

    @Autowired
    private RabbitRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Rabbit> allRabbits() {
        List<Rabbit> rabb = repository.findAll();
        if (rabb != null) {
            logger.info("Retrieved all rabbits: " + rabb);
        } else {
            logger.warn("No rabbits found");
        }
        return rabb != null ? rabb : Collections.emptyList();
    }

    public Optional<Rabbit> findRabbitBySerialNum(String tagNum) {
        Optional<Rabbit> rabbit = repository.findRabbitBytagNo(tagNum);
        if (rabbit.isPresent()) {
            logger.info("Found rabbit by tag number {}: {}", tagNum, rabbit.get());
        } else {
            logger.warn("No rabbit found with tag number {}", tagNum);
        }
        return rabbit;
    }

    public List<Rabbit> getRabbitsInCage(String cageNo) {
        List<Rabbit> rabb = repository.findByCage(cageNo);
        if (rabb != null) {
            logger.info("Retrieved rabbits in cage {}: {}", cageNo, rabb);
        } else {
            logger.warn("No rabbits found in cage {}", cageNo);
        }
        return rabb != null ? rabb : Collections.emptyList();
    }

    public List<Rabbit> getSameSexRabbits(String sex) {
        List<Rabbit> rabb = repository.findBySex(sex);
        if (rabb != null) {
            logger.info("Retrieved rabbits of sex {}: {}", sex, rabb);
        } else {
            logger.warn("No rabbits found for sex {}", sex);
        }
        return rabb != null ? rabb : Collections.emptyList();
    }

    public Rabbit createRabbit(Rabbit newRabbit) {
        logger.info("Creating rabbit with details: {}", newRabbit);
        System.out.println("BIRTHDAY ::: " + newRabbit.getBirthday());

        Rabbit savedRabbit = repository.save(newRabbit);

        // Uncomment and modify this section if needed
        /*
        if (savedRabbit != null) {
            String cageTag = savedRabbit.getCage();
            Query query = new Query(Criteria.where("cageTag").is(cageTag));
            Update update = new Update().push("rabbitsInCage", savedRabbit.getTagNo());

            mongoTemplate.updateFirst(query, update, Cage.class);

            savedRabbit.setCage(cageTag);
            repository.save(savedRabbit);
        }
        */

        logger.info("Saved rabbit: {}", savedRabbit);
        return savedRabbit;
    }

    // Commented out the old methods for reference
    /*
    public Rabbit createRabbit(Rabbit newRabbit) {
        Rabbit rabbit = repository.insert(new Rabbit(newRabbit));

        mongoTemplate.update(Cage.class)
                .matching(Criteria.where("cageTag").is(cage))
                .apply(new Update().push("rabbitsInCage").value(rabbit.getTagNo()))
                .first();
        return rabbit;
    }
    */
}
