package one.pawadtech.Rabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(MatingService.class);

    @Autowired
    private MatingRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Mating> findAllMating() {
        List<Mating> mating = repository.findAll();
        if (mating == null) {
            logger.warn("No mating records found.");
            return Collections.emptyList();
        }
        logger.info("Found {} mating records.", mating.size());
        return mating;
    }

    Optional<Mating> findByMating_id(String id) {
        Optional<Mating> mating = repository.findByMatingNum(id);
        if (mating.isPresent()) {
            logger.info("Found mating record with ID: {}", id);
        } else {
            logger.warn("No mating record found with ID: {}", id);
        }
        return mating;
    }

    public Optional<Mating> findMatingByRabbit(String tagNo) {
        Optional<Mating> mating = repository.findMatingByFemale(tagNo);
        if (mating.isPresent()) {
            logger.info("Found mating record for rabbit tag: {}", tagNo);
        } else {
            logger.warn("No mating record found for rabbit tag: {}", tagNo);
        }
        return mating;
    }

    public Optional<Mating> findLatestMatingByFemale(String tagNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("female").is(tagNo));
        query.limit(1);
        query.with(Sort.by(Sort.Order.asc("mate_from")));

        Mating latestMating = mongoTemplate.findOne(query, Mating.class);
        if (latestMating != null) {
            logger.info("Found latest mating record for female rabbit tag: {}", tagNo);
        } else {
            logger.warn("No latest mating record found for female rabbit tag: {}", tagNo);
        }
        return Optional.ofNullable(latestMating);
    }

    public Optional<Mating> findLatestMatingByMale(String tagNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("male").is(tagNo));
        query.limit(1);
        query.with(Sort.by(Sort.Order.asc("mate_from")));

        Mating latestMating = mongoTemplate.findOne(query, Mating.class);
        if (latestMating != null) {
            logger.info("Found latest mating record for male rabbit tag: {}", tagNo);
        } else {
            logger.warn("No latest mating record found for male rabbit tag: {}", tagNo);
        }
        return Optional.ofNullable(latestMating);
    }

    public Mating addNewMatingRecord(String mating_id, String male, String female, Date healthCheckedDate, boolean femaleHealthOkay, boolean maleHealthOkay, Date mateFrom, Date mateTo,
                                     Date fLastMate, Date mLastMate, String cage, String pregnancyConfirmed, String pregnancy_confirmedDate,
                                     Date expectedBirthDate, Date cagePrepDate, String actualBirthDate) {

        Mating newMatingRecord = new Mating(mating_id, male, female, healthCheckedDate, femaleHealthOkay, maleHealthOkay, mateFrom, mateTo, fLastMate, mLastMate,
                cage, pregnancyConfirmed, pregnancy_confirmedDate, expectedBirthDate, cagePrepDate, actualBirthDate);

        logger.info("Inserting a new mating record with ID: {}", mating_id);
        return repository.save(newMatingRecord);
    }

    public List<Mating> getMatingObjectsWithNullHealthCheckedDate() {
        List<Mating> matings = repository.findByHealthCheckedDateIsNull();
        logger.info("Found {} mating records with null health checked date.", matings.size());
        return matings;
    }

    public Mating updateMatingAttributes(String matingId, Map<String, Object> updatedAttributes) {
        Optional<Mating> existingMatingOptional = findByMating_id(matingId);

        if (existingMatingOptional.isPresent()) {
            Mating existingMating = existingMatingOptional.get();
            logger.info("Updating mating record with ID: {}", matingId);

            for (Map.Entry<String, Object> entry : updatedAttributes.entrySet()) {
                String attributeName = entry.getKey();
                Object attributeValue = entry.getValue();
                updateAttribute(existingMating, attributeName, attributeValue);
            }

            Mating updatedMating = repository.save(existingMating);
            logger.info("Successfully updated mating record with ID: {}", matingId);
            return updatedMating;
        } else {
            logger.warn("No mating record found with ID: {}", matingId);
            return null; // or throw an exception, depending on your use case
        }
    }

    private void updateAttribute(Mating mating, String attributeName, Object attributeValue) {
        try {
            mating.getClass().getMethod("set" + attributeName, attributeValue.getClass()).invoke(mating, attributeValue);
            logger.debug("Updated attribute {} with value {}", attributeName, attributeValue);
        } catch (Exception e) {
            logger.error("Failed to update attribute {} with value {}", attributeName, attributeValue, e);
        }
    }
}
