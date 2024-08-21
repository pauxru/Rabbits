package one.pawadtech.Rabbits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "TaskUpdateData")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateData {

    private static final Logger logger = LoggerFactory.getLogger(TaskUpdateData.class);

    private String taskRef;
    private String taskCloseDate;
    private Map<String, String> answerData;

    // Getters and setters

    public String getTaskRef() {
        logger.debug("Getting taskRef: {}", taskRef);
        return taskRef;
    }

    public String getTaskCloseDate() {
        logger.debug("Getting taskCloseDate: {}", taskCloseDate);
        return taskCloseDate;
    }

    public Map<String, String> getAnswerData() {
        logger.debug("Getting answerData: {}", answerData);
        return answerData;
    }
}
