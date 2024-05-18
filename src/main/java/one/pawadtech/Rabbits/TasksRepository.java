package one.pawadtech.Rabbits;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TasksRepository extends MongoRepository<Tasks, String> {

    List<Tasks> findByTaskIntroDate(String taskIntroDate);

    List<Tasks> findByClosedFalse();

    Optional<Tasks> findByTaskRef(String taskRef);
}
