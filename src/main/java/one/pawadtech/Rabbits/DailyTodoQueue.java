package one.pawadtech.Rabbits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Document(collection = "DailyQueue")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyTodoQueue {
    @Id
    private ObjectId objectId;
    private String queueId;
    private Date queueIntroDate;
    private Boolean closed;
    private Date queueCloseDate;
    private List<String> taskList;

    public DailyTodoQueue(String queueId, Date queueIntroDate, Boolean closed, Date queueCloseDate, List<String> taskList){
        this.queueId = queueId;
        this.queueIntroDate = queueIntroDate;
        this.closed = closed;
        this.queueCloseDate = queueCloseDate;
        this.taskList = taskList;
    }

    public String getQueueId() {
        return queueId;
    }

    public Date getQueueIntroDate() {
        return queueIntroDate;
    }

    public Boolean getClosed() {
        return closed;
    }

    public Date getQueueCloseDate() {
        return queueCloseDate;
    }

    public List<String> getTaskList() {
        return taskList;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public void setQueueIntroDate(Date queueIntroDate) {
        this.queueIntroDate = queueIntroDate;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public void setQueueCloseDate(Date queueCloseDate) {
        this.queueCloseDate = queueCloseDate;
    }

    public void setTaskList(List<String> taskList) {
        this.taskList = taskList;
    }
}
