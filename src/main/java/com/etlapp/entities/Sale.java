package com.etlapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sale")
public class Sale implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 6001073825986988159L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(name = "upcased_item_name")
    private String upcasedItemName;
    
    @Column(name = "squared_price")
    private Double squaredPrice;
    
    @Column(name = "total_amount")
    private Double totalAmount;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "sale_date")
    private Date date;

    public Sale() {}

    public Sale(String upcasedItemName, Double squaredPrice, Double totalAmount, Date date) {
        super();
        this.upcasedItemName = upcasedItemName;
        this.squaredPrice = squaredPrice;
        this.totalAmount = totalAmount;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getUpcasedItemName() {
        return upcasedItemName;
    }

    public Double getSquaredPrice() {
        return squaredPrice;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
    
    public Date getDate() {
        return date;
    }

    public void setUpcasedItemName(String upcasedItemName) {
        this.upcasedItemName = upcasedItemName;
    }

    public void setSquaredPrice(Double squaredPrice) {
        this.squaredPrice = squaredPrice;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((squaredPrice == null) ? 0 : squaredPrice.hashCode());
        result = prime * result + ((totalAmount == null) ? 0 : totalAmount.hashCode());
        result = prime * result + ((upcasedItemName == null) ? 0 : upcasedItemName.hashCode());
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
        Sale other = (Sale) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (squaredPrice == null) {
            if (other.squaredPrice != null)
                return false;
        } else if (!squaredPrice.equals(other.squaredPrice))
            return false;
        if (totalAmount == null) {
            if (other.totalAmount != null)
                return false;
        } else if (!totalAmount.equals(other.totalAmount))
            return false;
        if (upcasedItemName == null) {
            if (other.upcasedItemName != null)
                return false;
        } else if (!upcasedItemName.equals(other.upcasedItemName))
            return false;
        return true;
    }
    
}
