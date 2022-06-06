package vn.edu.tdc.tourguide.models;

import java.util.Date;

public class EventSchedule {

    public EventSchedule() {
    }


    public EventSchedule(String scheduleId, String destinationId, String userId, String nameDestination, String timeEvent, int dateEvent, int monthEvent, int yearEvent, String noteEvent) {
        this.scheduleId = scheduleId;
        this.destinationId = destinationId;
        this.userId = userId;
        this.nameDestination = nameDestination;
        this.timeEvent = timeEvent;
        this.dateEvent = dateEvent;
        this.monthEvent = monthEvent;
        this.yearEvent = yearEvent;
        this.noteEvent = noteEvent;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getYearEvent() {
        return yearEvent;
    }

    public void setYearEvent(int yearEvent) {
        this.yearEvent = yearEvent;
    }

    public String getNoteEvent() {
        return noteEvent;
    }

    public void setNoteEvent(String noteEvent) {
        this.noteEvent = noteEvent;
    }

    private String scheduleId;
    private String destinationId;
    private String userId;
    private String nameDestination;
    private String timeEvent;
    private int dateEvent;
    private int monthEvent;
    private int yearEvent;
    private String noteEvent;



}
