package com.secondhome.activities.login;


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
