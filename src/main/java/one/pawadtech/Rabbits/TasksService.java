package one.pawadtech.Rabbits;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TasksService {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private DateService dateService;


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

    public void deleteTask(String taskId) {
        tasksRepository.deleteById(taskId);
    }
}

