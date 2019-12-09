package com.example.tabbedactivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TueFragment extends Fragment {
    private static final String TAG = "TueFragment";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private String[] unitCodes = {"413", "416", "419", "403", "401"};
    private String[] startTimes = {"7", "9", "11", "2", "4"};
    private String[] endTimes = {"9", "11", "12", "4", "6"};
    private String[] unitNames = {"Principles of Functional Programming", "Data Mining", "Intelligent Agents",
            "Computer Design Lab", "Computer Systems Engineering I"};
    private String[] lecturers = {"Kinya", "Kinya", "Mugoye", "Nyabundi", "McOyowo"};
    private String[] rooms = {"TB3", "LAB3", "NL6", "ELAB", "TB3"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_layoutfile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listItems = new ArrayList<>();

        for (int i = 0; i < unitCodes.length; i++) {
            ListItem listItem = new ListItem(
                    startTimes[i] + ":00 - " + endTimes[i] + ":00",
                    "CCT " + unitCodes[i] + " | " + rooms[i],
                    unitNames[i],
                    lecturers[i]
            );
            listItems.add(listItem);
        }

        adapter = new MyAdapter(listItems, getContext());
        recyclerView.setAdapter(adapter);
    }
}