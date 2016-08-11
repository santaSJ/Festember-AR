package com.example.santa.nittmaps;

// class that returns POIs

/***************************************************
 Change this Class and get the data from API Call
 ****************************************************/

import java.util.ArrayList;
import java.util.List;


public class PointOfInterestManager
{
    public static List<PointOfInterest> getPOIs()
    {
        List<PointOfInterest> pois = new ArrayList<>();
        pois.add(new PointOfInterest("Lecture Hall Complex",10.761027,78.814204 ));
        pois.add( new PointOfInterest("Garnet C", 10.763411,78.812537));
        pois.add(new PointOfInterest("Opal",10.757885 , 78.820690));
        pois.add(new PointOfInterest("Garnet B", 10.762979 ,78.811539));
        pois.add(new PointOfInterest("Garnet A", 10.762629 ,78.811543 ));
        return  pois;
    }
}