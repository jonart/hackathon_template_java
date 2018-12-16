package msk.android.academy.javatemplate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import msk.android.academy.javatemplate.network.response.FilmModel;

public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private GridLayoutManager manager;
    private List<FilmModel> filmList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillList();
        recyclerView = view.findViewById(R.id.movie_item);
        manager = new GridLayoutManager(getContext(),2);
        adapter = new MovieAdapter(getActivity(),filmList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void fillList(){
        filmList = new ArrayList<>();
        filmList.add(new FilmModel(1,"1","Venom",2018));
        filmList.add(new FilmModel(2,"2","Beauty and Beast",2014));
        filmList.add(new FilmModel(3,"3","Troy",2004));
        filmList.add(new FilmModel(4,"4","Bunker",2004));
    }
}
