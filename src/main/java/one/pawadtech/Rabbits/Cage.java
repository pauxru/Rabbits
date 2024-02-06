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

    }
