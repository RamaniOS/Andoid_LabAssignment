package com.example.andoid_labassignment.ui.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andoid_labassignment.Adapter.PlaceAdapter;
import com.example.andoid_labassignment.Database.PlaceServiceImpl;
import com.example.andoid_labassignment.Models.Place;
import com.example.andoid_labassignment.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView listView;
    private PlaceAdapter placeAdapter;
    private PlaceServiceImpl placeService;
    private List<Place> placeList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        placeService = new PlaceServiceImpl(getContext());
        getPlacesFromDB();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initRecyclerView(View view) {
        listView = view.findViewById(R.id.recycle_list);
        placeAdapter = new PlaceAdapter(placeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL));

        listView.setLayoutManager(layoutManager);
        listView.setAdapter(placeAdapter);
    }

    public void getPlacesFromDB() {
       new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                placeList.clear();
                placeList.addAll(placeService.getAll());
                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {
                placeAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}