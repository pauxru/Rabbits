package one.pawadtech.Rabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TasksService {

    private static final Logger logger = LoggerFactory.getLogger(TasksService.class);

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private DateService dateService;

    public List<Tasks> getOpenTasks() {
        logger.info("Retrieving open tasks");
        return tasksRepository.findByClosedFalse();
    }

    public List<Tasks> allTasks() {
        logger.info("Entering allTasks function");
        System.out.println("Here at service allTasks function");
        List<Tasks> tasks = tasksRepository.findAll();
        logger.info("Retrieved all tasks: {}", tasks);
        return tasks;
    }

    public Optional<Tasks> getTaskById(String taskId) {
        logger.info("Retrieving task by ID: {}", taskId);
        return tasksRepository.findById(taskId);
    }

    public List<Tasks> getTasksByIntroDate(String introDate) {
        logger.info("Retrieving tasks by intro date: {}", introDate);
        return tasksRepository.findByTaskIntroDate(introDate);
    }

    public Tasks createTask(Tasks task) {
        logger.info("Creating task: {}", task);
        return tasksRepository.save(task);
    }

    public Tasks updateTask(String taskId, Tasks updatedTask) {
        logger.info("Updating task with ID: {}", taskId);

        Optional<Tasks> existingTaskOptional = tasksRepository.findById(taskId);
        if (existingTaskOptional.isPresent()) {
            Tasks existingTask = existingTaskOptional.get();
            BeanUtils.copyProperties(updatedTask, existingTask, "taskId");
            Tasks savedTask = tasksRepository.save(existingTask);
            logger.info("Updated task: {}", savedTask);
            return savedTask;
        }

        logger.warn("Task with ID: {} not found", taskId);
        return null; // or throw an exception, depending on your use case
    }

    public void updateTasks(List<TaskUpdateData> updateRequests) {
        logger.info("Updating tasks with update requests: {}", updateRequests);
        for (TaskUpdateData updateRequest : updateRequests) {
            String taskRef = updateRequest.getTaskRef();
            String taskCloseDate = updateRequest.getTaskCloseDate();
            Map<String, String> answerData = updateRequest.getAnswerData();

            System.out.println("Answer DATA::: " + answerData);
            logger.info("Processing update for taskRef: {}", taskRef);

            Optional<Tasks> existingTaskOptional = tasksRepository.findByTaskRef(taskRef);
            if (existingTaskOptional.isPresent()) {
                Tasks existingTask = existingTaskOptional.get();
                existingTask.setTaskCloseDate(answerData.get("actionDate"));
                existingTask.setAnswerData(answerData.get("taskActionComments"));
                existingTask.setClosed(true);

                tasksRepository.save(existingTask);
                logger.info("Task updated: {}", existingTask);
            } else {
                logger.warn("Task with taskRef: {} not found", taskRef);
            }
        }
    }

    public void deleteTask(String taskId) {
        logger.info("Deleting task with ID: {}", taskId);
        tasksRepository.deleteById(taskId);
        logger.info("Task with ID: {} deleted", taskId);
    }
}
