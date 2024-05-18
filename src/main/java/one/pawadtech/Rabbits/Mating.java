package one.pawadtech.Rabbits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Document(collection = "Mating")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mating {
    @Id
    private ObjectId sysMatingId;
    private String matingNum;
    private String male;
    private String female;
    private Date healthCheckedDate;
    private boolean femaleHealthOkay;
    private boolean maleHealthOkay;
    private Date mate_from;
    private Date mate_to;
    private Date f_last_mate;
    private Date m_last_mate;
    private String cage;
    private String pregnancy_confirmed;
    private String pregnancy_confirmedDate;
    private Date expected_birth_date;
    private Date cage_prep_date;
    private String actualBirthDate;

    public Mating(String matingNum, String male, String female, Date healthCheckedDate, boolean femaleHealthOkay, boolean maleHealthOkay, Date mate_from, Date mate_to, Date f_last_mate, Date m_last_mate, String cage, String pregnancy_confirmed, String pregnancy_confirmedDate, Date expected_birth_date, Date cage_prep_date, String actualBirthDate){
        this.matingNum = matingNum;
        this.male = male;
        this.female = female;
        this.healthCheckedDate = healthCheckedDate;
        this.femaleHealthOkay = femaleHealthOkay;
        this.maleHealthOkay = maleHealthOkay;
        this.mate_from = mate_from;
        this.mate_to = mate_to;
        this.f_last_mate = f_last_mate;
        this.m_last_mate = m_last_mate;
        this.cage = cage;
        this.pregnancy_confirmed = pregnancy_confirmed;
        this.pregnancy_confirmedDate = pregnancy_confirmedDate;
        this.expected_birth_date = expected_birth_date;
        this.cage_prep_date = cage_prep_date;
        this.actualBirthDate = actualBirthDate;
    }


    public String getMale() {
        return male;
    }

    public String getFemale() {
        return female;
    }

    public Date getHealth_checked_date() {
        return healthCheckedDate;
    }

    public String getMatingNum() {
        return matingNum;
    }

    public Date getMate_from() {
        return mate_from;
    }

    public Date getMate_to() {
        return mate_to;
    }

    public Date getF_last_mate() {
        return f_last_mate;
    }

    public Date getM_last_mate() {
        return m_last_mate;
    }

    public String getCage() {
        return cage;
    }

    public String getPregnancy_confirmed() {
        return pregnancy_confirmed;
    }

    public String getPregnancy_confirmedDate() {
        return pregnancy_confirmedDate;
    }

    public void setPregnancy_confirmedDate(String pregnancy_confirmedDate) {
        this.pregnancy_confirmedDate = pregnancy_confirmedDate;
    }

    public Date getExpected_birth_date() {
        return expected_birth_date;
    }

    public Date getCage_prep_date() {
        return cage_prep_date;
    }

    public void setMatingNum(String matingNum) {
        this.matingNum = matingNum;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public void setFemale(String female) {
        this.female = female;
    }

    public void setHealth_checked_date(Date health_checked_date) {
        this.healthCheckedDate = health_checked_date;
    }

    public void setMate_from(Date mate_from) {
        this.mate_from = mate_from;
    }

    public void setMate_to(Date mate_to) {
        this.mate_to = mate_to;
    }

    public void setF_last_mate(Date f_last_mate) {
        this.f_last_mate = f_last_mate;
    }

    public void setM_last_mate(Date m_last_mate) {
        this.m_last_mate = m_last_mate;
    }

    public void setHealthCheckedDate(Date healthCheckedDate) {
        this.healthCheckedDate = healthCheckedDate;
    }

    public void setFemaleHealthOkay(boolean femaleHealthOkay) {
        this.femaleHealthOkay = femaleHealthOkay;
    }

    public void setMaleHealthOkay(boolean maleHealthOkay) {
        this.maleHealthOkay = maleHealthOkay;
    }

    public Date getHealthCheckedDate() {
        return healthCheckedDate;
    }

    public boolean isFemaleHealthOkay() {
        return femaleHealthOkay;
    }

    public boolean isMaleHealthOkay() {
        return maleHealthOkay;
    }

    public String getActualBirthDate() {
        return actualBirthDate;
    }

    public void setActualBirthDate(String actualBirthDate) {
        this.actualBirthDate = actualBirthDate;
    }

    public void setCage(String cage) {
        this.cage = cage;
    }

    public void setPregnancy_confirmed(String pregnancy_confirmed) {
        this.pregnancy_confirmed = pregnancy_confirmed;
    }

    public void setExpected_birth_date(Date expected_birth_date) {
        this.expected_birth_date = expected_birth_date;
    }

    public void setCage_prep_date(Date cage_prep_date) {
        this.cage_prep_date = cage_prep_date;
    }
}
