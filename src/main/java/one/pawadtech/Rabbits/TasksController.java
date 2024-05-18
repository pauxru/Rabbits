package one.pawadtech.Rabbits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private TasksService tasksService;

    private final DateService dateService;
    @Autowired
    public TasksController(DateService dateService) {
        this.dateService = dateService;
    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Tasks>> getAllTask(){
        System.out.println("Here at getAllTask function");
        return new ResponseEntity<List<Tasks>>(tasksService.getOpenTasks(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateTasks(@RequestBody List<TaskUpdateData> updateRequests) {
        System.out.println("Answer DATA::: "+ updateRequests);
        try {
            tasksService.updateTasks(updateRequests);
            return ResponseEntity.ok("Data updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating data");
        }
    }

    @GetMapping("/getTodayTasks")
    public ResponseEntity<List<Tasks>> getTodayTasks(){
        System.out.println("Here at getTodayTasks function");
        String introDate = dateService.getTodayDateString();
        System.out.println("The date ::: "+ introDate);
        return new ResponseEntity<List<Tasks>>(tasksService.getTasksByIntroDate(introDate), HttpStatus.OK);
    }


}
