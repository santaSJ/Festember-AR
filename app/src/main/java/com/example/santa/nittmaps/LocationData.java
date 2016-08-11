package com.example.santa.nittmaps;


import android.location.Location;

// Class to get berings and distances between two places`
public class LocationData
{
    // Gets LOS distance between two places
    public static double getDistance( Location p1, Location p2)
    {
        double dlat = p1.getLatitude() - p2.getLatitude();
        dlat = toRadian(dlat);
        double dlon = p1.getLongitude() - p2.getLongitude();
        dlon = toRadian(dlon);
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(toRadian(p1.getLatitude())) * Math.cos(toRadian(p2.getLatitude())) * Math.pow(Math.sin(dlon / 2), 2);
        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6373 * b;
    }

    // gets bearing frm first place to the second
    public static double getBearing(Location myLocation , Location destination)
    {

        double dLon = destination.getLongitude() - myLocation.getLongitude();

        double x = Math.cos(toRadian(destination.getLatitude())) * Math.sin(toRadian(dLon));
        double y = Math.cos(toRadian(myLocation.getLatitude())) * Math.sin(toRadian(destination.getLatitude())) - Math.sin(toRadian(myLocation.getLatitude())) * Math.cos(toRadian(destination.getLatitude())) * Math.cos(toRadian(dLon));
        double value = toDegree(Math.atan2(x, y));
        if (value < 0)
        {
            value = 360 + value;
        }
        return value;
    }

    // Convert DEgree to Rad
    private static double toRadian( double deg)
    {
        return deg * Math.PI / 180;
    }

    // Convert Radian to Degree
    private static double toDegree( double rad)
    {
        return rad * 180 / Math.PI;
    }
}
