package one.pawadtech.Rabbits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Document(collection = "Rabbits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kindling {
    @Id
    private ObjectId birth_id;
    private String mating_id;
    private String female;
    private String male;
    private Date birth_date;
    private String no_of_kittens;
    private Date sep_with_mom_date;
    private String cage;
    private String survived_kittens;

    public Kindling(String mating_id, String female, String male, Date birth_date, String no_of_kittens, Date sep_with_mom_date, String cage, String survived_kittens){
        this.birth_id = birth_id;
        this.mating_id = mating_id;
        this.female = female;
        this.male = male;
        this.birth_date = birth_date;
        this.no_of_kittens = no_of_kittens;
        this.sep_with_mom_date = sep_with_mom_date;
        this.cage = cage;
        this.survived_kittens = survived_kittens;
    }


    public String getMating_id() {
        return mating_id;
    }

    public String getFemale() {
        return female;
    }

    public String getMale() {
        return male;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public String getNo_of_kittens() {
        return no_of_kittens;
    }

    public Date getSep_with_mom_date() {
        return sep_with_mom_date;
    }

    public String getCage() {
        return cage;
    }

    public String getSurvived_kittens() {
        return survived_kittens;
    }

    public void setMating_id(String mating_id) {
        this.mating_id = mating_id;
    }

    public void setFemale(String female) {
        this.female = female;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setNo_of_kittens(String no_of_kittens) {
        this.no_of_kittens = no_of_kittens;
    }

    public void setSep_with_mom_date(Date sep_with_mom_date) {
        this.sep_with_mom_date = sep_with_mom_date;
    }

    public void setCage(String cage) {
        this.cage = cage;
    }

    public void setSurvived_kittens(String survived_kittens) {
        this.survived_kittens = survived_kittens;
    }
}
