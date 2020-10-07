package no.hiof.snailey.familyplaner.model;

import com.google.firebase.firestore.Exclude;

public class todo {

    @Exclude
    private String userID;
    private String title;
    private String description;
    private boolean active;
    private String family;

    public todo() {};

    public todo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public todo(String title, String description, boolean active, String family) {
        this.title = title;
        this.description = description;
        this.active = active;
        this.family = family;
    }

    @Exclude
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
