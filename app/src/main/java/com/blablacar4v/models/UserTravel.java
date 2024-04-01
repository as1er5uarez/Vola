package com.blablacar4v.models;

public class UserTravel {
    private int idTravel;
    private String emailUser;

    public UserTravel(int idTravel, String emailUser) {
        this.idTravel = idTravel;
        this.emailUser = emailUser;
    }

    public int getIdTravel() {
        return idTravel;
    }

    public void setIdTravel(int idTravel) {
        this.idTravel = idTravel;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }


}
