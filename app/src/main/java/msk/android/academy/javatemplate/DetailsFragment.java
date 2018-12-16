package msk.android.academy.javatemplate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    ProgressBar progress;
    TextView describtion, country, timing;
    ImageView image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.details_fragment,container,false);
         return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       // super.onViewCreated(view, savedInstanceState);
        progress = view.findViewById(R.id.progress_bar);
        describtion = view.findViewById(R.id.description);
        country = view.findViewById(R.id.country);
    }
}
