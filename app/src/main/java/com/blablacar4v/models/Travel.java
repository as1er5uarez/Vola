package com.blablacar4v.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Travel implements Parcelable {
    private int id;

    private String departureHour;
    private String arrivalHour;
    private String departureDate;
    private String departurePlace;
    private String arrivalPlace;
    private String description;
    private String userPublicated;
    private int seats;
    private ArrayList<User> usersInterested;

    public Travel() {
    }

    public static final Creator<Travel> CREATOR = new Creator<Travel>() {
        @Override
        public Travel createFromParcel(Parcel in) {
            return new Travel(in);
        }

        @Override
        public Travel[] newArray(int size) {
            return new Travel[size];
        }
    };

    public Travel(String arrivalHour, String arrivalPlace, String departureDate, String departureHour, String departurePlace, String description,  int seats, String userPublicated, int id) {
        this.arrivalHour = arrivalHour;
        this.arrivalPlace = arrivalPlace;
        this.departureDate = departureDate;
        this.departureHour = departureHour;
        this.departurePlace = departurePlace;
        this.description = description;
        this.id = id;
        this.userPublicated = userPublicated;
        this.seats = seats;

    }

    public Travel(Parcel in) {
        departureHour = in.readString();
        arrivalHour = in.readString();
        departureDate = in.readString();
        departurePlace = in.readString();
        arrivalPlace = in.readString();
        description = in.readString();
        userPublicated = in.readString();
        seats = in.readInt();
        id  = in.readInt();
    }

    public void addUserInterested(User user){
        usersInterested.add(user);
    }

    public void removeUserInterested(User user){
        usersInterested.remove(user);
    }


    public String getUserPublicated() {
        return userPublicated;
    }

    public void setUserPublicated(String userPublicated) {
        this.userPublicated = userPublicated;
    }

    public String getDepartureHour() {
        return departureHour;
    }

    public void setDepartureHour(String departureHour) {
        this.departureHour = departureHour;
    }

    public String getArrivalHour() {
        return arrivalHour;
    }

    public void setArrivalHour(String arrivalHour) {
        this.arrivalHour = arrivalHour;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getArrivalPlace() {
        return arrivalPlace;
    }

    public void setArrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(departureHour);
        dest.writeString(arrivalHour);
        dest.writeString(departureDate);
        dest.writeString(departurePlace);
        dest.writeString(arrivalPlace);
        dest.writeString(description);
        dest.writeString(userPublicated);
        dest.writeInt(seats);
        dest.writeInt(id);
    }
}
