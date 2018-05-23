package com.kutaycandan.instainsight.model;

import java.io.Serializable;

public class InstaUserModelFollowerCount implements Serializable {
    String Pk;
    String ProfilePicture;
    String UserName;
    String FullName;
    String Type;
    String TimeTabel;
    int FollowerCount;

    public int getFollowerCount() {
        return FollowerCount;
    }

    public void setFollowerCount(int followerCount) {
        FollowerCount = followerCount;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTimeTabel() {
        return TimeTabel;
    }

    public void setTimeTabel(String timeTabel) {
        TimeTabel = timeTabel;
    }

    public String getPk() {
        return Pk;
    }

    public void setPk(String pk) {
        Pk = pk;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }
}
