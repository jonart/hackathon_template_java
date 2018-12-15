package msk.android.academy.javatemplate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import msk.android.academy.javatemplate.network.response.ResModel;

public class Adapter extends RecyclerView.Adapter<FilmViewHolder> {

    private final List<ResModel> mFilms = new ArrayList<>();

    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder filmViewHolder, int position) {
        ResModel resModel = mFilms.get(position);
        filmViewHolder.bind(resModel);
    }

    @Override
    public int getItemCount() {
        return mFilms.size();
    }

    public void addData(List<ResModel> data) {
        mFilms.addAll(data);
        notifyDataSetChanged();
    }
}
