package com.jaydeemanuel.finalproj.ui.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jaydeemanuel.finalproj.R;
import com.jaydeemanuel.finalproj.data.DatabaseHelper;
import com.jaydeemanuel.finalproj.data.NoteModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    NotesAdapter notesAdapter = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.rvNotes);
        DatabaseHelper db = new DatabaseHelper(root.getContext());
        List<NoteModel> data = db.getAllNotes();
        notesAdapter = new NotesAdapter(
                data,
                new NotesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(NoteModel item) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("noteID", item.noteID);
                        Navigation.findNavController(root).navigate(R.id.nav_edit_note, bundle);
                    }
                },
                new NotesAdapter.OnDeleteClickListener() {
                    @Override
                    public void onItemClick(NoteModel item, int position) {
                        db.deleteNote(item.noteID);
                        ((List<?>) data).remove(position);
                        notesAdapter.notifyDataSetChanged();
                        Snackbar.make(root, item.noteFname + " has been deleted.", Snackbar.LENGTH_LONG).show();
                    }
                },
                root.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(notesAdapter);

        FloatingActionButton fabAddNote = root.findViewById(R.id.fabAddNote);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_add_note);
            }
        });



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}