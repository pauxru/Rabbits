package one.pawadtech.Rabbits;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatingService {
    @Autowired
    private MatingRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Mating> findAllMating(){

        List<Mating> mating = repository.findAll();
        return mating != null ? mating : Collections.emptyList();
    }

    Optional<Mating> findByMating_id(String id){
        return repository.findByMatingNum(id);
    }
     public Optional<Mating> findMatingByRabbit(String TagNo){
         return repository.findMatingByFemale(TagNo);
     }

    public Optional<Mating> findLatestMatingByFemale(String tagNo){
        Query query = new Query();
        query.addCriteria(Criteria.where("female").is(tagNo));
        query.limit(1);
        query.with(Sort.by(Sort.Order.asc("mate_from")));

        Mating latestMating = mongoTemplate.findOne(query, Mating.class);

        return Optional.ofNullable(latestMating);
    }
    public Optional<Mating> findLatestMatingByMale(String tagNo){
        Query query = new Query();
        query.addCriteria(Criteria.where("male").is(tagNo));
        query.limit(1);
        query.with(Sort.by(Sort.Order.asc("mate_from")));

        Mating latestMating = mongoTemplate.findOne(query, Mating.class);

        return Optional.ofNullable(latestMating);
    }

    public Mating addNewMatingRecord(String mating_id, String male, String female, Date healthCheckedDate, Date mateFrom, Date mateTo,
                                     Date fLastMate, Date mLastMate, String cage, String pregnancyConfirmed,
                                     Date expectedBirthDate, Date cagePrepDate) {

        Mating newMatingRecord = new Mating(mating_id, male, female, healthCheckedDate, mateFrom, mateTo, fLastMate, mLastMate,
                cage, pregnancyConfirmed, expectedBirthDate, cagePrepDate);

        System.out.println("Inserted a new Mating class record");
        return repository.save(newMatingRecord);
    }

    public List<Mating> getMatingObjectsWithNullHealthCheckedDate() {
        return repository.findByHealthCheckedDateIsNull();
    }

    public Mating updateMatingAttributes(String matingId, Map<String, Object> updatedAttributes) {
        Optional<Mating> existingMatingOptional = findByMating_id(matingId);

        if (existingMatingOptional.isPresent()) {
            Mating existingMating = existingMatingOptional.get();
            // Update attributes individually
            for (Map.Entry<String, Object> entry : updatedAttributes.entrySet()) {
                String attributeName = entry.getKey();
                Object attributeValue = entry.getValue();

                // Update the attribute using reflection or specific setter methods
                updateAttribute(existingMating, attributeName, attributeValue);
            }

            // Save the updated Mating document
            return repository.save(existingMating);
        }

        return null; // or throw an exception, depending on your use case
    }

    // Helper method to update a specific attribute in the Mating object
    private void updateAttribute(Mating mating, String attributeName, Object attributeValue) {
        // You can use reflection or specific setter methods based on your preference
        // For example, using reflection:
        try {
            mating.getClass().getMethod("set" + attributeName, attributeValue.getClass()).invoke(mating, attributeValue);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the reflection exception appropriately
        }
    }
}
