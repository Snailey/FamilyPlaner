package no.hiof.snailey.familyplaner.model;

public class shopping {

    private String title;
    private int number;
    private boolean active;
    private String family;

    public shopping() {};

    public shopping(String title, int number, boolean active, String family) {
        this.title = title;
        this.number = number;
        this.active = active;
        this.family = family;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
