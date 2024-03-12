package one.pawadtech.Rabbits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {
    @Id
    private ObjectId sys;
    private String taskRef;
    private String taskType;
    private String matingIdRef;
    private String taskIntroDate;
    private boolean closed;
    private String taskCloseDate;
    private String taskMessage;

    public Tasks(String taskRef, String taskType, String matingIdRef, String taskIntroDate, boolean closed, String taskCloseDate, String taskMessage) {
        this.taskRef = taskRef;
        this.taskType = taskType;
        this.matingIdRef = matingIdRef;
        this.taskIntroDate = taskIntroDate;
        this.closed = closed;
        this.taskCloseDate = taskCloseDate;
        this.taskMessage = taskMessage;
    }

    // getters and setters

    public String getTaskID() {
        return taskRef;
    }

    public void setTaskID(String taskID) {
        this.taskRef = taskID;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getMatingIdRef() {
        return matingIdRef;
    }

    public void setMatingIdRef(String matingIdRef) {
        this.matingIdRef = matingIdRef;
    }

    public String getTaskIntroDate() {
        return taskIntroDate;
    }

    public void setTaskIntroDate(String taskIntroDate) {
        this.taskIntroDate = taskIntroDate;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getTaskCloseDate() {
        return taskCloseDate;
    }

    public void setTaskCloseDate(String taskCloseDate) {
        this.taskCloseDate = taskCloseDate;
    }

    public String getTaskMessage() {
        return taskMessage;
    }

    public void setTaskMessage(String taskMessage) {
        this.taskMessage = taskMessage;
    }
}
