package one.pawadtech.Rabbits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "TaskUpdateData")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateData {
    private String taskRef;
    private String taskCloseDate;
    private Map<String, String> answerData;

    // Getters and setters

    public String getTaskRef() {
        return taskRef;
    }

    public String getTaskCloseDate() {
        return taskCloseDate;
    }

    public Map<String, String> getAnswerData() {
        return answerData;
    }
}