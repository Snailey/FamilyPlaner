package no.hiof.snailey.familyplaner.model;

import java.util.Date;

public class calendar {
    private Date starttime;
    private Date endtime;
    private String title;
    private String description;
    private String family;

    public calendar(Date starttime, Date endtime, String title, String description, String family) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.title = title;
        this.description = description;
        this.family = family;

    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
