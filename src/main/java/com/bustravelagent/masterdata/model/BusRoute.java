package com.bustravelagent.masterdata.model;

import jakarta.persistence.*;

@Entity
@Table(name = "busroute")
public class BusRoute {
    @Id
    @Column(name = "bus_no", nullable = false, unique = true)
    private Integer busNo;

    @Column(name = "source", length = 50)
    private String source;

    @Column(name = "destination", length = 50)
    private String destination;

    @Column(name = "price", nullable = false)
    private Integer price;

    public Integer getBusNo() {
        return busNo;
    }

    public void setBusNo(Integer busNo) {
        this.busNo = busNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BusRoute{" +
                "busNo=" + busNo +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", price=" + price +
                '}';
    }
}
