package com.jaydeemanuel.finalproj.ui.notes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jaydeemanuel.finalproj.R;
import com.jaydeemanuel.finalproj.data.NoteModel;


import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<NoteModel> localDataSet;
    private final OnItemClickListener localListener;
    private final OnDeleteClickListener localListenerFave;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(NoteModel item);
    }

    public interface OnDeleteClickListener {
        void onItemClick(NoteModel item, int position);
    }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fname;
        private final TextView lname;
        private final TextView dose;
        private final TextView result;
        private final CardView cardView;
        private final Button btnDelete;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            fname = view.findViewById(R.id.fname);
            lname = view.findViewById(R.id.lname);
            dose = view.findViewById(R.id.dose);
            result = view.findViewById(R.id.result);
            cardView = view.findViewById(R.id.noteCardView);
            btnDelete = view.findViewById(R.id.btnDelete);
        }

        public TextView getTitle() {
            return fname;
        }
        public TextView getDescription() {
            return lname;
        }
        public TextView getDose() {return dose;}
        public TextView getResult() {return result;}
        public CardView getCardView() {
            return cardView;
        }
        public Button getBtnDelete() {
            return btnDelete;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public NotesAdapter(List<NoteModel> dataSet, OnItemClickListener listener, OnDeleteClickListener listenerFave, Context context) {

        localDataSet = dataSet;
        localListener = listener;
        localListenerFave = listenerFave;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTitle().setText(localDataSet.get(position).noteFname);
        viewHolder.getDescription().setText(localDataSet.get(position).noteLname);
        viewHolder.getDose().setText(localDataSet.get(position).noteDose);
        viewHolder.getResult().setText(localDataSet.get(position).noteDate);
        viewHolder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localListener.onItemClick(localDataSet.get(position));
            }
        });
        viewHolder.getBtnDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localListenerFave.onItemClick(localDataSet.get(position), position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

