package one.pawadtech.Rabbits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private TasksService tasksService;

    private DateService dateService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Tasks>> getAllTask(){
        System.out.println("Here at getAllTask function");
        return new ResponseEntity<List<Tasks>>(tasksService.allTasks(), HttpStatus.OK);
    }

    @GetMapping("/getTodayTasks")
    public ResponseEntity<List<Tasks>> getTodayTasks(){
        System.out.println("Here at getTodayTasks function");
        String introDate = dateService.getTodayDateString();
        return new ResponseEntity<List<Tasks>>(tasksService.getTasksByIntroDate(introDate), HttpStatus.OK);
    }

}
