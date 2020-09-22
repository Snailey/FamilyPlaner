package no.hiof.snailey.familyplaner.model;

public class todo {

    private String title;
    private String description;
    private boolean active;
    private String family;

    public todo(String title, String description, boolean active, String family) {
        this.title = title;
        this.description = description;
        this.active = active;
        this.family = family;
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
