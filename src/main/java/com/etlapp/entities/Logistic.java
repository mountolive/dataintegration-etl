package com.etlapp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "logistic")
public class Logistic implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2674058833531720985L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "upcased_name")
    private String upcasedName;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "squared_amount")
    private Double squaredAmount;
    
    @Column(name = "action_date")
    @Temporal(TemporalType.TIME)
    private LocalDateTime date;

    public Logistic() {}
    
    public Logistic(String upcasedName, String description, Double squaredAmount, LocalDateTime date) {
        this.upcasedName = upcasedName;
        this.description = description;
        this.squaredAmount = squaredAmount;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getUpcasedName() {
        return upcasedName;
    }
    
    public String getDescription() {
        return description;
    }

    public Double getSquaredAmount() {
        return squaredAmount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setUpcasedName(String upcasedName) {
        this.upcasedName = upcasedName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setSquaredAmount(Double squaredAmount) {
        this.squaredAmount = squaredAmount;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((squaredAmount == null) ? 0 : squaredAmount.hashCode());
        result = prime * result + ((upcasedName == null) ? 0 : upcasedName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Logistic other = (Logistic) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (squaredAmount == null) {
            if (other.squaredAmount != null)
                return false;
        } else if (!squaredAmount.equals(other.squaredAmount))
            return false;
        if (upcasedName == null) {
            if (other.upcasedName != null)
                return false;
        } else if (!upcasedName.equals(other.upcasedName))
            return false;
        return true;
    }
}
