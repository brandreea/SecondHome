package com.secondhome.data.model.others;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    public String getUserEmail() {
        return userEmail;
    }

    private String userEmail;
    private String displayName;
    private String UID;
    public LoggedInUser(String uid,String userEmail, String displayName) {
        this.userEmail = userEmail;
        this.displayName = displayName;
        this.UID=uid;
    }

    public String getUID(){ return UID; }
    public String getDisplayName() {
        return displayName;
    }
}