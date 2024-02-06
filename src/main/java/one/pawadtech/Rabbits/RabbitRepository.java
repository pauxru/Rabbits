package one.pawadtech.Rabbits;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface RabbitRepository extends MongoRepository<Rabbit, ObjectId> {
    Optional<Rabbit> findRabbitBytagNo(String tagNo);

    List<Rabbit> findByCage(String cage);
}
