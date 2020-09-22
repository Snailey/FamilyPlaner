package no.hiof.snailey.familyplaner.model;

class User {
    private int userID;
    private String name;
    private String email;
    private String family;

    public User(int userID, String name, String email, String family ) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.family = family;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
