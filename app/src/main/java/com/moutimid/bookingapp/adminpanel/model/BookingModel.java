package com.moutimid.bookingapp.adminpanel.model;

public class BookingModel {
    private String key, time, name, contact_no, buzzer_no, no_of_guest;
    boolean booked, seated;

    public BookingModel(String key, String time, String name, String contact_no, String buzzer_no, String no_of_guest, boolean booked, boolean seated) {
        this.key = key;
        this.time = time;
        this.name = name;
        this.contact_no = contact_no;
        this.buzzer_no = buzzer_no;
        this.no_of_guest = no_of_guest;
        this.booked = booked;
        this.seated = seated;
    }

    public BookingModel(String key, String time, String name, String contact_no, String buzzer_no, String no_of_guest) {
        this.key = key;
        this.time = time;
        this.name = name;
        this.contact_no = contact_no;
        this.buzzer_no = buzzer_no;
        this.no_of_guest = no_of_guest;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public boolean isSeated() {
        return seated;
    }

    public void setSeated(boolean seated) {
        this.seated = seated;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getBuzzer_no() {
        return buzzer_no;
    }

    public void setBuzzer_no(String buzzer_no) {
        this.buzzer_no = buzzer_no;
    }

    public String getNo_of_guest() {
        return no_of_guest;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setNo_of_guest(String no_of_guest) {
        this.no_of_guest = no_of_guest;
    }
}
