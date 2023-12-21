package com.jaydeemanuel.finalproj.ui.notes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.jaydeemanuel.finalproj.R;
import com.jaydeemanuel.finalproj.data.DatabaseHelper;
import com.jaydeemanuel.finalproj.data.NoteModel;

import java.util.Calendar;

public class EditNoteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public EditNoteFragment() {
        // Required empty public constructor
    }

    public static EditNoteFragment newInstance(String param1, String param2) {
        EditNoteFragment fragment = new EditNoteFragment();
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
        View root = inflater.inflate(R.layout.fragment_edit_note, container, false);

        EditText tvFname = root.findViewById(R.id.tvFname);
        EditText tvLname = root.findViewById(R.id.tvLname);
        Spinner tvDose = root.findViewById(R.id.tvDose);
        Button btnUpdate = root.findViewById(R.id.btnUpdate);
        Button btnCancel = root.findViewById(R.id.btnCancel);

        DatabaseHelper db = new DatabaseHelper(root.getContext());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.vaccine, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tvDose.setAdapter(adapter);

        Button btDate = root.findViewById(R.id.btDate);
        final TextView tvResult = root.findViewById(R.id.tvResult);

        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                tvResult.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        NoteModel note = db.getNote(getArguments().getInt("noteID"));

        tvLname.setText(note.noteLname);
        tvFname.setText(note.noteFname);
        tvResult.setText(note.noteDate);

        int position = adapter.getPosition(note.noteDose);
        tvDose.setSelection(position);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvFname.getText().toString().isEmpty() || tvLname.getText().toString().isEmpty() || tvDose.getSelectedItem().toString().isEmpty() || tvResult.getText().toString().isEmpty()) {
                    Snackbar.make(root, "Please enter note title and/or description", Snackbar.LENGTH_SHORT).show();
                } else {
                    long result = db.updateNote(
                            getArguments().getInt("noteID"),
                            tvFname.getText().toString(),
                            tvLname.getText().toString(),
                            tvResult.getText().toString(),
                            tvDose.getSelectedItem().toString());

                    if (result < 0) {
                        Snackbar.make(root, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(root, "Your note has been updated.", Snackbar.LENGTH_SHORT).show();
                        Navigation.findNavController(root).navigateUp();
                    }
                }
            }
        });




        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigateUp();
            }
        });

        return root;
    }
}
