package xyz.vivekc.webrtccodelab;

import android.util.Log;

import org.webrtc.CameraVideoCapturer;

/**
 * Webrtc_Step2
 * Created by vivek-3102 on 11/03/17.
 */

public class CustomCameraEventsHandler implements CameraVideoCapturer.CameraEventsHandler {

    private String logTag = this.getClass().getCanonicalName();


    @Override
    public void onCameraError(String s) {
        Log.d(logTag, "onCameraError() called with: s = [" + s + "]");
    }

    @Override
    public void onCameraFreezed(String s) {
        Log.d(logTag, "onCameraFreezed() called with: s = [" + s + "]");
    }

    @Override
    public void onCameraOpening(int i) {
        Log.d(logTag, "onCameraOpening() called with: i = [" + i + "]");
    }

    @Override
    public void onFirstFrameAvailable() {
        Log.d(logTag, "onFirstFrameAvailable() called");
    }

    @Override
    public void onCameraClosed() {
        Log.d(logTag, "onCameraClosed() called");
    }
}
