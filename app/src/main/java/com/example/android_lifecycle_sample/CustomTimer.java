package com.example.android_lifecycle_sample;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * This is a class representing a timer that you can start or stop. The secondsCount outputs a count of
 * how many seconds since it started, every one second.
 * <p>
 * -----
 * <p>
 * Handler and Runnable are beyond the scope of this lesson. This is in part because they deal with
 * threading, which is a complex topic that will be covered in a later lesson.
 * <p>
 * If you want to learn more now, you can take a look on the Android Developer documentation on
 * threading:
 * <p>
 * https://developer.android.com/guide/components/processes-and-threads
 */
class CustomTimer  {

    public static final String TAG = "CustomTimer";

    // The number of seconds counted since the timer started
    int secondsCount = 0;




    /**
     * [Handler] is a class meant to process a queue of messages (known as [android.os.Message]s)
     * or actions (known as [Runnable]s)
     */
    private Handler handler = new Handler();
    private Runnable runnable;


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void startTimer() {
        // Create the runnable action, which prints out a log and increments the seconds counter
        runnable = new Runnable() {
            @Override
            public void run() {
                secondsCount++;
                Log.i(TAG, "Timer is at : "+secondsCount);
                // postDelayed re-adds the action to the queue of actions the Handler is cycling
                // through. The delayMillis param tells the handler to run the runnable in
                // 1 second (1000ms)
                handler.postDelayed(runnable, 1000);
            }
        };

        // This is what initially starts the timer
        handler.postDelayed(runnable, 1000);

        // Note that the Thread the handler runs on is determined by a class called Looper.
        // In this case, no looper is defined, and it defaults to the main or UI thread.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopTimer() {
        // Removes all pending posts of runnable from the handler's queue, effectively stopping the
        // timer
        handler.removeCallbacks(runnable);
    }
}
