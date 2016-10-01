package akirachix.com.movieapp;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author Thomas Kioko
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private static AppController mAppController;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppController = this;
    }

    /**
     * Helper method to return an instance on AppController class
     *
     * @return {@link AppController} instance
     */
    public static synchronized AppController getInstance() {
        return mAppController;
    }

    /**
     * Helper method to get all request in the queue
     *
     * @return {@link RequestQueue}
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Generic method to add requests to volley
     *
     * @param request Request
     * @param tag     Request tag used to track the request
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        // set the default tag if tag is empty
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

}

