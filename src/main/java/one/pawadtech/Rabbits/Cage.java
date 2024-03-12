package one.pawadtech.Rabbits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "cages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cage {
    @Id
    private ObjectId cageNo;
    private String cageTag;
    private Integer capacity;
    private Integer rabbitsInCage;
    private String maturityDesignation;
    //@DocumentReference
    @DBRef
    private List<Rabbit> tagNo;

    public String getCageTag() {
        return cageTag;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getRabbitsInCage() {
        return rabbitsInCage;
    }

    public String getMaturityDesignation() {
        return maturityDesignation;
    }

    public List<Rabbit> getTagNo() {
        return tagNo;
    }

    public void setCageTag(String cageTag) {
        this.cageTag = cageTag;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setRabbitsInCage(Integer rabbitsInCage) {
        this.rabbitsInCage = rabbitsInCage;
    }

    public void setMaturityDesignation(String maturityDesignation) {
        this.maturityDesignation = maturityDesignation;
    }

    public void setTagNo(List<Rabbit> tagNo) {
        this.tagNo = tagNo;
    }
}
