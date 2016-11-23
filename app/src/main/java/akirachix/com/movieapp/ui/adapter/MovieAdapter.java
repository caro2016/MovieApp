 package akirachix.com.movieapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import akirachix.com.movieapp.R;
import akirachix.com.movieapp.models.Movie;

/**
 * Created by Student on 10/1/2016.
 */

public class MovieAdapter extends BaseAdapter {
    List<Movie> movieList;
    Context context;
    public MovieAdapter(Context context, List<Movie> movieList){
        this.movieList =movieList;
        this.context= context;

    }


    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create an instance
        LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rootView = layoutInflater.inflate(R.layout.movie_list_item ,null);
        ImageView moviePoster = (ImageView) rootView.findViewById(R.id.moviePoster);
        TextView MovieTitle = (TextView) rootView.findViewById(R.id.movieTitle);
        //create an instance of the movie object
        Movie movie = movieList.get(position);
        MovieTitle.setText(movie.getTitle());
        Glide.with(context)
                .load(movie.getPosterPath())
                .into(moviePoster);
        return rootView;
    }
}
