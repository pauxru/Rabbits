package one.pawadtech.Rabbits;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface TasksRepository extends MongoRepository<Tasks, String> {

    List<Tasks> findByTaskIntroDate(String taskIntroDate);
}
