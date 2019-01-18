package util;

import java.time.LocalDate;


public class BatchTM {
    private String b_id;
    private String c_id;
    private String description;
    private LocalDate date;
    private int capacity;

    public BatchTM(String b_id, String c_id, String description, LocalDate date, int capacity) {
        this.b_id = b_id;
        this.c_id = c_id;
        this.description = description;
        this.date = date;
        this.capacity = capacity;
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
