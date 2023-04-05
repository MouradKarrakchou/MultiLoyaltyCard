package fr.polytech.entities;

import fr.polytech.entities.structure.TerritorialCommunity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;
@Entity
public class  Advantage {

    //private TerritorialCommunity provider;
    private String advantageName;
    private Date expirationDate;
    private int counter;
    @GeneratedValue
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
