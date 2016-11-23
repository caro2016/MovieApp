 package akirachix.com.movieapp.ui;

 import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import akirachix.com.movieapp.AppController;
import akirachix.com.movieapp.R;
import akirachix.com.movieapp.models.Movie;
import akirachix.com.movieapp.ui.adapter.MovieAdapter;
import akirachix.com.movieapp.utils.AppConstants;

/**
 * @author Thomas Kioko
 */
public class MainActivity extends AppCompatActivity {

    //Declare Global variables
    private List<Movie>movieList = new ArrayList<>();
    private GridView mGridView;
    private ProgressDialog mProgressDialog;
    /**
* Variable to get the class name. This will be used for debugging
     */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise views
        mGridView = (GridView) findViewById(R.id.gridView);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading..."); //Set the message to display on the dialog

        //call method to fetch popular movies
        getPopularMovies();
    }

    /**
     * Method to get popular movies by using {@link com.android.volley.toolbox.Volley} to handle all
     * HTTP Requests.
     * <p/>
     * For more information on volley, {@see <href="https://developer.android.com/training/volley/index.html">}
     */
    private void getPopularMovies() {
        //Show the dialog
        mProgressDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                AppConstants.END_POINT,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Print the response from the server
                        Log.i(LOG_TAG, response.toString());

                        //Close the dialog
                        mProgressDialog.dismiss();

                        // Parse the JSON Response.
                        try {
                            //get array from the JSON Object
                            JSONArray jsonArray=response.getJSONArray("results");
                            //loop through the array and get Json  objects in the array
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonMovieObject=jsonArray.getJSONObject(i);
                                String movieTitle= jsonMovieObject.getString("title");
                                String moviePosterPath=jsonMovieObject.getString("poster_path");
                                //Image url
                                String imageUrl=AppConstants.TMDB_IMAGE_URL + AppConstants.IMAGE_SIZE_500 + moviePosterPath;
                                Log.i(LOG_TAG, "Movie Title" + movieTitle);
                                Movie movie = new Movie();
                                movie.setTitle(movieTitle);
                                movie.setPosterPath(imageUrl);

                                movieList.add(movie);
                            }
                            mGridView.setAdapter(new MovieAdapter(MainActivity.this,movieList));
                        } catch (JSONException e) {
                            Log.e(LOG_TAG, e.getLocalizedMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(LOG_TAG, "Error: " + error.getMessage());

                // hide the progress dialog
                mProgressDialog.dismiss();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "POPULAR_MOVIE_TAG ");
    }
}
