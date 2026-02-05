package org.example.web3.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.inject.Named;
import jakarta.faces.event.ActionEvent;

@Named("timeBean")
@SessionScoped
public class TimeBean implements Serializable {

    private String currentTime;
    private String currentDate;


    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @PostConstruct
    public void init() {
        updateTime();
    }

    public void updateTime() {
        Date now = new Date();
        currentTime = timeFormat.format(now);
        currentDate = dateFormat.format(now);
    }

    public void updateTime(ActionEvent event) {
        updateTime();
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
