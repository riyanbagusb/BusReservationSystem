package com.bagus.busreservationsystem.models;

import com.google.gson.annotations.SerializedName;

public class Ticket {
    @SerializedName("id")
    private Integer id;

    @SerializedName("seatNumber")
    private Integer seatNumber;

    @SerializedName("cancellable")
    private Boolean cancellable;

    @SerializedName("journeyDate")
    private String journeyDate;

    public Ticket() {
    }

    public Ticket(Integer id, Integer seatNumber, Boolean cancellable, String journeyDate) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.cancellable = cancellable;
        this.journeyDate = journeyDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Boolean getCancellable() {
        return cancellable;
    }

    public void setCancellable(Boolean cancellable) {
        this.cancellable = cancellable;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }
}
