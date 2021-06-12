package com.github.scott.workouttracking.data;

public class History {
    String userId = "";
    String distance = "";
    String averageSpeed= "";
    String calories= "";

    public History(String userId, String distance, String averageSpeed, String calories) {
        this.userId = userId;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        this.calories = calories;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
