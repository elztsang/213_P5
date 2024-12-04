package com.example.androidpizzaria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pizzaria.Topping;

/**
 * This is an Adapter class to be used to instantiate an adapter for the RecyclerView.
 * Must extend RecyclerView.Adapter, which will enforce you to implement 3 methods:
 *      1. onCreateViewHolder, 2. onBindViewHolder, and 3. getItemCount
 *
 * You must use the data type <thisClassName.yourHolderName>, in this example
 * <ItemAdapter.ItemHolder>. This will enforce you to define a constructor for the
 * ItemAdapter and an inner class ItemsHolder (a static class)
 * The ItemsHolder class must extend RecyclerView.ViewHolder. In the constructor of this class,
 * you do something similar to the onCreate() method in an Activity.
 * @author Lily Chang
 */
class ToppingsAdapter extends RecyclerView.Adapter<ToppingsAdapter.ToppingsHolder>{
    private static final int MAX_SELECTION = 7;
    private Context context; //need the context to inflate the layout
    private ArrayList<Topping> toppings; //need the data binding to each row of RecyclerView

    private ArrayList<Topping> selectedToppings = new ArrayList<>();

    private boolean isSelectionEnabled;  // Flag to control selection

    public ToppingsAdapter(Context context, ArrayList<Topping> toppings, ArrayList<Topping> preselectedToppings, boolean isSelectionEnabled) { //TODO: add a parameter for preselected toppings?
        this.context = context;
        this.toppings = toppings;
        selectedToppings.addAll(preselectedToppings);
        this.isSelectionEnabled = isSelectionEnabled;  // Store the flag
    }

    public void setSelectedToppings(ArrayList<Topping> selectedToppings) {
        this.selectedToppings = selectedToppings;
    }

    public void setSelectionEnabled(boolean selectionEnabled) {
        isSelectionEnabled = selectionEnabled;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ToppingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the row layout for the items
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view, parent, false);
        return new ToppingsHolder(view);
    }

    /**
     * Assign data values for each row according to their "position" (index) when the item becomes
     * visible on the screen.
     * @param holder the instance of ItemsHolder
     * @param position the index of the item in the list of items
     */
    @Override
    public void onBindViewHolder(@NonNull ToppingsHolder holder, int position) {
        Topping topping = toppings.get(position);

        holder.textView.setText(toppings.get(position).name());
//        holder.imageView.setImageResource(toppings.get(position).getImage()); //need to resolve this

        holder.checkBox.setEnabled(isSelectionEnabled);  // If false, checkbox won't be interactive

        holder.checkBox.setChecked(selectedToppings.contains(topping));

        //todo: need to check this
        holder.checkBox.setEnabled(selectedToppings.contains(topping) || selectedToppings.size() < MAX_SELECTION);

        holder.itemView.setOnClickListener(v -> {
            if (holder.checkBox.isEnabled()) {
                if (holder.checkBox.isChecked()) {
                    selectedToppings.remove(topping);
                } else {
                    if (selectedToppings.size() < MAX_SELECTION) {
                        selectedToppings.add(topping);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    /**
     * Get the number of items in the ArrayList.
     * @return the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return toppings.size(); //number of MenuItem in the array list.
    }

    /**
     * Get the views from the row layout file, similar to the onCreate() method.
     */
    public static class ToppingsHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;
        ImageView imageView;

        public ToppingsHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemText);
            checkBox = itemView.findViewById(R.id.itemCheckBox);
        }
    }
}

