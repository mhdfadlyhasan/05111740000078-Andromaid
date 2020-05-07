package com.hzzzey.andromaid;

public class task_schedule_item {
    private int id, type;
    private String name,time,Date;

    public int getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return Date;
    }

    public task_schedule_item() {

    }
    public task_schedule_item(int id, String name, String description, String place, String time, int type, String Date ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.place = place;
        this.time = time;
        this.type = type;
        this.Date = Date;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void settype(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    String description;
    String place;

    public int getId() {
        return id;
    }

    public int gettype() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

}
