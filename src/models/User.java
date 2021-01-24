package models;

public class User {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public User(String id, String name, String mobileNumber, String emailId) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    private String id;
    private String name;
    private String mobileNumber;
    private String emailId;

    public String toString() {
        return "{ id: " + this.id
                + " name: " + this.name
                + " mobileNumber: " + this.mobileNumber
                + " emailId: " + this.emailId
                + "}";
    }
}
