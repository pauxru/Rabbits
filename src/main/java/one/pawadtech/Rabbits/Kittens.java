package one.pawadtech.Rabbits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Kittens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kittens {
    @Id
    private ObjectId birth_id;
    private String cage;
    private String total_in_cage;
    private Date sex_reveal_date;
    private String no_of_female;
    private String no_of_male;
    private Date sep_on_sex_date;
    private Date sep_with_mom_date;

    public Kittens(String cage, String total_in_cage, Date sex_reveal_date, String no_of_female, String no_of_male, Date sep_on_sex_date, Date sep_with_mom_date) {
        this.birth_id = birth_id;
        this.cage = cage;
        this.total_in_cage = total_in_cage;
        this.sex_reveal_date = sex_reveal_date;
        this.no_of_female = no_of_female;
        this.no_of_male = no_of_male;
        this.sep_on_sex_date = sep_on_sex_date;
        this.sep_with_mom_date = sep_with_mom_date;
    }

    public String getCage() {
        return cage;
    }

    public String getTotal_in_cage() {
        return total_in_cage;
    }

    public Date getSex_reveal_date() {
        return sex_reveal_date;
    }

    public String getNo_of_female() {
        return no_of_female;
    }

    public String getNo_of_male() {
        return no_of_male;
    }

    public Date getSep_on_sex_date() {
        return sep_on_sex_date;
    }

    public Date getSep_with_mom_date() {
        return sep_with_mom_date;
    }

    public void setCage(String cage) {
        this.cage = cage;
    }

    public void setTotal_in_cage(String total_in_cage) {
        this.total_in_cage = total_in_cage;
    }

    public void setSex_reveal_date(Date sex_reveal_date) {
        this.sex_reveal_date = sex_reveal_date;
    }

    public void setNo_of_female(String no_of_female) {
        this.no_of_female = no_of_female;
    }

    public void setNo_of_male(String no_of_male) {
        this.no_of_male = no_of_male;
    }

    public void setSep_on_sex_date(Date sep_on_sex_date) {
        this.sep_on_sex_date = sep_on_sex_date;
    }

    public void setSep_with_mom_date(Date sep_with_mom_date) {
        this.sep_with_mom_date = sep_with_mom_date;
    }
}
