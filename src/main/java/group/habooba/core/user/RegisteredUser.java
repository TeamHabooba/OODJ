package group.habooba.core.user;

import group.habooba.core.repository.StringSerializable;

public class RegisteredUser extends User implements StringSerializable {
    private Profile profile;


    public RegisteredUser() {
        super();
        this.profile = new Profile("", "");
    }

    public Profile getProfile() {
        return profile;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    // Not implemented
    @Override
    public String toString(){
        return "";
    }

    //Not implemented
    @Override
    public void fromString(String string) {

    }
}
