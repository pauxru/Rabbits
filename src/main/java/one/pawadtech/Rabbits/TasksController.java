package one.pawadtech.Rabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    private static final Logger logger = LoggerFactory.getLogger(TasksController.class);

    @Autowired
    private TasksService tasksService;

    private final DateService dateService;

    @Autowired
    public TasksController(DateService dateService) {
        this.dateService = dateService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Tasks>> getAllTask() {
        logger.info("Entering getAllTask function");
        System.out.println("Here at getAllTask function");

        List<Tasks> tasks = tasksService.getOpenTasks();
        logger.info("Retrieved open tasks: {}", tasks);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateTasks(@RequestBody List<TaskUpdateData> updateRequests) {
        logger.info("Update request received: {}", updateRequests);
        System.out.println("Answer DATA::: " + updateRequests);

        try {
            tasksService.updateTasks(updateRequests);
            logger.info("Tasks updated successfully");
            return ResponseEntity.ok("Data updated successfully");
        } catch (Exception e) {
            logger.error("Error updating tasks", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating data");
        }
    }

    @GetMapping("/getTodayTasks")
    public ResponseEntity<List<Tasks>> getTodayTasks() {
        logger.info("Entering getTodayTasks function");
        System.out.println("Here at getTodayTasks function");

        String introDate = dateService.getTodayDateString();
        System.out.println("The date ::: " + introDate);

        List<Tasks> tasks = tasksService.getTasksByIntroDate(introDate);
        logger.info("Retrieved tasks for date {}: {}", introDate, tasks);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
