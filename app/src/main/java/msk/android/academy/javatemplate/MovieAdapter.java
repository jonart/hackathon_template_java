package msk.android.academy.javatemplate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import msk.android.academy.javatemplate.network.response.FilmModel;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private Context context;
    private List<FilmModel> movieList;

    public MovieAdapter(Context context, List<FilmModel> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_adapter, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        movieHolder.name.setText(movieList.get(i).getTitle());
        movieHolder.abstent.setText(R.string.disclaimer);   /// как бы заглушка

        Glide.with(context)
                .load("http://images.vfl.ru/ii/1533673160/b5567d64/22803905.jpg")
                .into(movieHolder.poster);

        movieHolder.movie.setOnClickListener(view -> {
            Bundle args = new Bundle();

            FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.container, new DetailsFragment())
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        TextView abstent, name;
        ImageView poster;
        CardView movie;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.movie_name);
            abstent = itemView.findViewById(R.id.abstent);
            poster = itemView.findViewById(R.id.poster);
            movie = itemView.findViewById(R.id.movie_view);


        }
    }
}
