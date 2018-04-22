package com.kutaycandan.instainsight.model;

import java.io.Serializable;

public class InstaUserProfileData implements Serializable {
    String Pk;
    String ProfilePicture;
    String UserName;
    int Follower;
    int Following;
    String PostCount;

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

    public int getFollower() {
        return Follower;
    }

    public void setFollower(int follower) {
        Follower = follower;
    }

    public int getFollowing() {
        return Following;
    }

    public void setFollowing(int following) {
        Following = following;
    }

    public String getPostCount() {
        return PostCount;
    }

    public void setPostCount(String postCount) {
        PostCount = postCount;
    }
}
