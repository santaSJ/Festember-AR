package com.example.santa.nittmaps;


import android.graphics.Color;
import android.location.Location;

// Model Class for all Point Of Interests

public class PointOfInterest
{

    public Location location;
    public String distance;

    public String Title;
    public String Description;

    public String type;

    public double left;
    public double top;

    public Color eventStatus;

    PointOfInterest( String title , double lat , double lon)
    {
        this.location = new Location("gps");
        this.left = 0;
        this.top = 80;
        this.Title = title;
        this.location.setLatitude(lat);
        this.location.setLongitude(lon);
    }

}
