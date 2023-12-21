package com.jaydeemanuel.finalproj.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaydeemanuel.finalproj.R;
import com.jaydeemanuel.finalproj.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(),2));

        ArrayList<recyclerview_list> recyclerview_list = new ArrayList<recyclerview_list>();
        recyclerview_list.add(new recyclerview_list(R.drawable.pfizer,"Pfizer"));
        recyclerview_list.add(new recyclerview_list(R.drawable.janssen,"Janssen"));
        recyclerview_list.add(new recyclerview_list(R.drawable.moderna,"Moderna"));
        recyclerview_list.add(new recyclerview_list(R.drawable.astrazeneca,"AstraZeneca"));
        recyclerview_list.add(new recyclerview_list(R.drawable.sputnik,"Sputnik"));
        recyclerview_list.add(new recyclerview_list(R.drawable.covax,"Covax"));



        recyclerview_adapter recyclerview_adapter = new recyclerview_adapter(recyclerview_list, root.getContext());
        recyclerView.setAdapter(recyclerview_adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}