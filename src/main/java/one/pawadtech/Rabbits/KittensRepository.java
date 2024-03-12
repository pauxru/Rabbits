package one.pawadtech.Rabbits;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KittensRepository extends MongoRepository<Kittens, ObjectId> {

}
