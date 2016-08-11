package com.example.santa.nittmaps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.util.DisplayMetrics;
import android.view.View;
import java.util.ArrayList;
import java.util.List;


public class VirtualLayer extends View
{
    // Bearing from the compass [0,360]
    double compassBearing;
    Location currentLocation = new Location("gps");

    // List of POIs in View of the user
    List<PointOfInterest> poisInView = new ArrayList<>();
    Context context;

    // Indicates if accurate location is available
    boolean isLoc = false;

    public VirtualLayer(Context context)
    {
        super(context);
        this.context = context;
        // Set default location
        currentLocation.setLatitude(10.761027);
        currentLocation.setLongitude(78.814204);
    }

    private double dp(double px)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = (float) (px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    // Sets compass bearings and updates layout
    public void setCompassBearing(double bearing)
    {
        compassBearing = bearing;
        updateTiles();
    }

    // sets current location and updates layout
    public void setCurrentLocation(Location  _currentLocation)
    {
        currentLocation = _currentLocation;
        updateTiles();
        isLoc = true;
    }

    //  Update tiles
    public void updateTiles()
    {
        getPoisInRange();
        int i;

        // Change top of POIs in View so that they dont overlap
        for (PointOfInterest poi : poisInView)
        {

            for (i = 0; i < poisInView.size(); i++)
            {
                PointOfInterest _poi = poisInView.get(i);
                if (Math.abs(poi.left - _poi.left) < 30)
                {
                    double dist1 = LocationData.getDistance(currentLocation, poi.location);
                    double dist2 = LocationData.getDistance(currentLocation, _poi.location);

                    if (dist2 > dist1)
                    {
                        _poi.top += 120;
                        poisInView.set(i, _poi);
                    } else
                    {
                        _poi.top -= 120;
                        poisInView.set(i, _poi);
                    }
                }
            }

        }

        if(isLoc)
        {
            invalidate();
        }
    }

    // Gets POIs in Range
    private void getPoisInRange()
    {
        List<PointOfInterest> allPOIs = PointOfInterestManager.getPOIs();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels ;
        float dpWidth = displayMetrics.widthPixels;
        poisInView.clear();
        for( PointOfInterest p: allPOIs)
        {
            //double bearing = LocationData.getBearing(currentLocation ,p.location);
            double bearing = currentLocation.bearingTo(p.location);
            double diff = Math.abs(compassBearing - bearing);
            p.top = dpHeight/2;
            p.left = 0;
            if( diff < 30 || diff > 330)
            {
                p.left = getLeft(dpWidth , compassBearing , bearing);
                p.distance = String.format("%.2f km", LocationData.getDistance(currentLocation, p.location));
                poisInView.add(p);

            }
        }


    }

    // Gets Left of POI tile from the difference in bearings
    private double getLeft(double actualWidth, double compass, double bearing)
    {
        double mid = actualWidth / 2;
        double div = mid / 30;
        if( compass < 180)
        {
            bearing -= compass;
        }
        else
        {
            bearing += (360 - compass);
            if(bearing > 90)
            {
                bearing = -(360 - bearing);
            }
        }
        if( bearing > 0)
        {
            double dist = mid + div * bearing - 35;
            return dist;
        }
        else
        {
            double dist =  mid - div *(-bearing) - 35;
            return dist;
        }
    }

    //Draw
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(70);
        paint.setAlpha(60);
        Paint p = new Paint(Color.RED);
        p.setTextSize(30);


        double maxHeight = canvas.getHeight(), maxWidth = canvas.getWidth();
        //canvas.drawCircle((float)maxWidth/360*(float)compassBearing, 100 , 100 , paint);

        double unit = maxWidth/60.0;

        for( PointOfInterest poi: poisInView)
        {
            canvas.drawRect( (float)poi.left, (float)poi.top, (float)poi.left + 200, (float)poi.top + 200 , paint);
            String title[] = poi.Title.split(" ");
            int top = 30;
            for ( String str: title)
            {
                canvas.drawText( str ,(float)poi.left + 10 , (float)poi.top + top, p);
                top += 30;
            }

            canvas.drawText( poi.distance ,(float)poi.left +10 , (float)poi.top + 200 - 30, p);
        }
        if(isLoc)
        {
            canvas.drawRect( 200 , 200 , 300 , 300 , paint);
        }


    }
}
