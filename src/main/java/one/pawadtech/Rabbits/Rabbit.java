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
public class Rabbit {
    @Id
    private ObjectId serialNo;
    private String tagNo;
    private String present;
    private Date birthday;
    private String breed;
    private String mother;
    private String father;
    private String sex;
    private String origin;
    private String diseases;
    private String comments;
    private String weight;
    private String price_sold;
    private String cage;
    private List<String> images;

    public Rabbit(String tagNo, String present, Date birthday, String breed, String mother, String father, String sex, String origin, String diseases, String comments, String weight, String price_sold, String cage, List<String> images) {
        this.tagNo = tagNo;
        this.birthday = birthday;
        this.present = present;
        this.breed = breed;
        this.mother = mother;
        this.father = father;
        this.sex = sex;
        this.origin = origin;
        this.diseases = diseases;
        this.comments = comments;
        this.weight = weight;
        this.price_sold = price_sold;
        this.cage = cage;
        this.images = images;
    }

    public String getTagNo() {
        return tagNo;
    }

    public String getPresent() {
        return present;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getBreed() {
        return breed;
    }

    public String getMother() {
        return mother;
    }

    public String getFather() {
        return father;
    }

    public String getSex() {
        return sex;
    }

    public String getDiseases() {
        return diseases;
    }

    public String getCage() {
        return cage;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setPrice_sold(String price_sold) {
        this.price_sold = price_sold;
    }

    public void setCage(String cage) {
        this.cage = cage;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
