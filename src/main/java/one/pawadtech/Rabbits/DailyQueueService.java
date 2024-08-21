package one.pawadtech.Rabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DailyQueueService {

    private static final Logger logger = LoggerFactory.getLogger(DailyQueueService.class);

    @Autowired
    private DailyTodoRepository dailyTodoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<DailyTodoQueue> getAllQueues() {
        logger.info("Fetching all queues from the database.");
        return dailyTodoRepository.findAll();
    }

    public List<DailyTodoQueue> getAllOpenTodos() {
        logger.info("Fetching all open todos from the database.");
        return dailyTodoRepository.findByClosed(false);
    }

    public void updateTaskList(String queueId, String newTask) {
        logger.info("Updating task list for queue ID: {}", queueId);

        Optional<DailyTodoQueue> optionalQueue = dailyTodoRepository.findByQueueId(queueId);

        if (optionalQueue.isPresent()) {
            DailyTodoQueue existingQueue = optionalQueue.get();
            List<String> currentTaskList = existingQueue.getTaskList();
            currentTaskList.add(newTask);  // Add the new task to the existing task list
            existingQueue.setTaskList(currentTaskList);
            dailyTodoRepository.save(existingQueue);
            logger.info("Queue updated with new task for ID: {}", queueId);
        } else {
            logger.warn("Specified Queue with ID {} is not present.", queueId);

            Date currentDate = new Date();
            addNewDailyTodoQueueRecord(queueId, currentDate, false, null, null);
            logger.info("New queue created with ID: {}", queueId);
            updateTaskList(queueId, newTask);  // Retry the update after creating the new queue
        }
    }

    public DailyTodoQueue addNewDailyTodoQueueRecord(String queueID, Date queueIntroDate, Boolean closed, Date queueCloseDate, List<String> taskList) {
        logger.info("Adding new daily todo queue record with ID: {}", queueID);
        DailyTodoQueue newDailyTodoQueue = new DailyTodoQueue(queueID, queueIntroDate, closed, queueCloseDate, taskList);
        return dailyTodoRepository.save(newDailyTodoQueue);
    }

}
