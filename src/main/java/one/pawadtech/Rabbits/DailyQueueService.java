package one.pawadtech.Rabbits;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DailyQueueService {
    @Autowired
    private DailyTodoRepository dailyTodoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<DailyTodoQueue> getAllQueues(){
        return dailyTodoRepository.findAll();
    }

    public List<DailyTodoQueue> getAllOpenTodos(){
        return dailyTodoRepository.findByClosed(false);
    }

    public void updateTaskList(String queueId, String newTask) {
        Optional<DailyTodoQueue> optionalQueue = dailyTodoRepository.findByQueueId(queueId);

        if (optionalQueue.isPresent()) {
            DailyTodoQueue existingQueue = optionalQueue.get();
            List<String> currentTaskList = existingQueue.getTaskList();
            currentTaskList.add(newTask); // Add the new task to the existing task list
            existingQueue.setTaskList(currentTaskList);
            dailyTodoRepository.save(existingQueue);
            System.out.println("Queue Updated with new task of ID ::: "+ queueId);
        } else {
            System.out.println("Specified Queue is not present");

            Date currentDate = new Date();

            addNewDailyTodoQueueRecord(queueId, currentDate, false, null, null);
            System.out.println("New Queue created of ID ::: "+ queueId);
            updateTaskList(queueId, newTask);
        }
    }
    public DailyTodoQueue addNewDailyTodoQueueRecord(String queueID, Date queueIntroDate, Boolean closed, Date queueCloseDate, List<String> taskList){
        DailyTodoQueue newDailyTodoQueue = new DailyTodoQueue(queueID, queueIntroDate, closed, queueCloseDate, taskList);
        return dailyTodoRepository.save(newDailyTodoQueue);
    }

}
