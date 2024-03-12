package one.pawadtech.Rabbits;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailyTodoRepository extends MongoRepository<DailyTodoQueue, ObjectId> {
List<DailyTodoQueue> findByClosed(Boolean status);

Optional<DailyTodoQueue> findByQueueId(String queueId);
}
