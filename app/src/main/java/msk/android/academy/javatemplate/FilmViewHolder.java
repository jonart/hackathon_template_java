package msk.android.academy.javatemplate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import msk.android.academy.javatemplate.network.response.ResModel;

public class FilmViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    private TextView mYear;

    public FilmViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_named);
        mYear = itemView.findViewById(R.id.tv_year);
    }

    public void bind(ResModel model){
        mTitle.setText(model.getTitle());
        mYear.setText(String.valueOf(model.getYear()));
    }
}
