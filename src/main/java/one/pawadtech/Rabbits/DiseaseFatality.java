package one.pawadtech.Rabbits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Diseases_fatalities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseFatality {
    @Id
    private ObjectId fatality_id;
    private String tagNo;
    private String disease;
    private Date notice_date;
    private String treatment_method;
    private String medication_used;
    private boolean quarantined;
    private boolean fatal;
    private Date recovery_date;

    public DiseaseFatality(String tagNo, String disease, Date notice_date, String treatment_method, String medication_used, boolean quarantined, boolean fatal, Date recovery_date) {
        this.fatality_id = fatality_id;
        this.tagNo = tagNo;
        this.disease = disease;
        this.notice_date = notice_date;
        this.treatment_method = treatment_method;
        this.medication_used = medication_used;
        this.quarantined = quarantined;
        this.fatal = fatal;
        this.recovery_date = recovery_date;
    }

    public String getTagNo() {
        return tagNo;
    }

    public String getDisease() {
        return disease;
    }

    public Date getNotice_date() {
        return notice_date;
    }

    public String getTreatment_method() {
        return treatment_method;
    }

    public String getMedication_used() {
        return medication_used;
    }

    public boolean isQuarantined() {
        return quarantined;
    }

    public boolean isFatal() {
        return fatal;
    }

    public Date getRecovery_date() {
        return recovery_date;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setNotice_date(Date notice_date) {
        this.notice_date = notice_date;
    }

    public void setTreatment_method(String treatment_method) {
        this.treatment_method = treatment_method;
    }

    public void setMedication_used(String medication_used) {
        this.medication_used = medication_used;
    }

    public void setQuarantined(boolean quarantined) {
        this.quarantined = quarantined;
    }

    public void setFatal(boolean fatal) {
        this.fatal = fatal;
    }

    public void setRecovery_date(Date recovery_date) {
        this.recovery_date = recovery_date;
    }
}
