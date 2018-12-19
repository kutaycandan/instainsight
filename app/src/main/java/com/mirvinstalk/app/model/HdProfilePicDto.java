package com.mirvinstalk.app.model;

import java.io.Serializable;

public class HdProfilePicDto implements Serializable {
    private String ProfilePicture;
    private String ProfilePictureHd;
    private String Bio;
    private Boolean IsPrivate;
    private Boolean IsVerified;
    private String FullName;
    private String ExternalUrl;
    private String Pk;
    private String UserName;
    private Integer Follower;
    private Integer Following;
    private Integer PostCount;


    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getProfilePictureHd() {
        return ProfilePictureHd;
    }

    public void setProfilePictureHd(String profilePictureHd) {
        ProfilePictureHd = profilePictureHd;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public Boolean getPrivate() {
        return IsPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        IsPrivate = aPrivate;
    }

    public Boolean getVerified() {
        return IsVerified;
    }

    public void setVerified(Boolean verified) {
        IsVerified = verified;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getExternalUrl() {
        return ExternalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        ExternalUrl = externalUrl;
    }

    public String getPk() {
        return Pk;
    }

    public void setPk(String pk) {
        Pk = pk;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Integer getFollower() {
        return Follower;
    }

    public void setFollower(Integer follower) {
        Follower = follower;
    }

    public Integer getFollowing() {
        return Following;
    }

    public void setFollowing(Integer following) {
        Following = following;
    }

    public Integer getPostCount() {
        return PostCount;
    }

    public void setPostCount(Integer postCount) {
        PostCount = postCount;
    }
}
