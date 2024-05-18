package one.pawadtech.Rabbits;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TasksService {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private DateService dateService;

    public List<Tasks> getOpenTasks() {
        return tasksRepository.findByClosedFalse();
    }

    public List<Tasks> allTasks() {
        System.out.println("Here at service allTasks function");
        return tasksRepository.findAll();
    }

    public Optional<Tasks> getTaskById(String taskId) {
        return tasksRepository.findById(taskId);
    }

    public List<Tasks> getTasksByIntroDate(String introDate) {
        return tasksRepository.findByTaskIntroDate(introDate);
    }

    public Tasks createTask(Tasks task) {
        return tasksRepository.save(task);
    }

    public Tasks updateTask(String taskId, Tasks updatedTask) {
        Optional<Tasks> existingTaskOptional = tasksRepository.findById(taskId);

        if (existingTaskOptional.isPresent()) {
            Tasks existingTask = existingTaskOptional.get();
            BeanUtils.copyProperties(updatedTask, existingTask, "taskId");
            return tasksRepository.save(existingTask);
        }

        return null; // or throw an exception, depending on your use case
    }

    public void updateTasks(List<TaskUpdateData> updateRequests) {
        for (TaskUpdateData updateRequest : updateRequests) {
            String taskRef = updateRequest.getTaskRef();
            String taskCloseDate = updateRequest.getTaskCloseDate();
            Map<String, String> answerData = updateRequest.getAnswerData();

            System.out.println("Answer DATA::: "+ answerData);

            Optional<Tasks> existingTaskOptional = tasksRepository.findByTaskRef(taskRef);
            if (existingTaskOptional.isPresent()) {
                Tasks existingTask = existingTaskOptional.get();
                existingTask.setTaskCloseDate(answerData.get("actionDate"));
                existingTask.setAnswerData(answerData.get("taskActionComments"));
                existingTask.setClosed(true);

                tasksRepository.save(existingTask);
            }
            // Handle cases where the taskRef doesn't exist if needed

        }
    }

    public void deleteTask(String taskId) {
        tasksRepository.deleteById(taskId);
    }
}

