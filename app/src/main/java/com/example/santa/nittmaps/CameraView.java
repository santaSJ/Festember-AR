package com.example.santa.nittmaps;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;


// View to get Camera preview

public class CameraView extends SurfaceView implements SurfaceHolder.Callback
{
    public SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraView(Context context, Camera camera)
    {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        mCamera.stopPreview();
        mCamera.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
    {

        if (mHolder.getSurface() == null){
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
