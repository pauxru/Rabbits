package one.pawadtech.Rabbits;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatingRepository extends MongoRepository<Mating, String> {
    Optional<Mating> findMatingByFemale(String tagNo);
    Optional<Mating> findByMatingNum(String id);
    List<Mating> findByHealthCheckedDateIsNull();



}
