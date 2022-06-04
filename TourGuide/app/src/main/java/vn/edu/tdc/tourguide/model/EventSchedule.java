package vn.edu.tdc.tourguide.model;

import java.util.Date;

public class EventSchedule {

    public EventSchedule( String nameDestination, String timeEvent, int dateEvent, int monthEvent, String noteEvent) {
        this.nameDestination = nameDestination;
        this.timeEvent = timeEvent;
        this.dateEvent = dateEvent;
        this.monthEvent = monthEvent;
        this.noteEvent = noteEvent;
    }


    public String getNameDestination() {
        return nameDestination;
    }

    public void setNameDestination(String nameDestination) {
        this.nameDestination = nameDestination;
    }

    public String getTimeEvent() {
        return timeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        this.timeEvent = timeEvent;
    }

    public int getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(int dateEvent) {
        this.dateEvent = dateEvent;
    }

    public int getMonthEvent() {
        return monthEvent;
    }

    public void setMonthEvent(int monthEvent) {
        this.monthEvent = monthEvent;
    }

    public String getNoteEvent() {
        return noteEvent;
    }

    public void setNoteEvent(String noteEvent) {
        this.noteEvent = noteEvent;
    }

    public EventSchedule() {
    }

    private String nameDestination;
    private String timeEvent;
    private int dateEvent;
    private int monthEvent;
    private String noteEvent;



}
