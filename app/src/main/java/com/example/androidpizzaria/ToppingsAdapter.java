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
import java.util.HashMap;
import java.util.Map;
import java.util.EnumMap;

import pizzaria.Topping;

/**
 * This is an Adapter class to be used to instantiate an adapter for the RecyclerView.
 * Must extend RecyclerView.Adapter, which will enforce you to implement 3 methods:
 * 1. onCreateViewHolder, 2. onBindViewHolder, and 3. getItemCount
 * <p>
 * You must use the data type <thisClassName.yourHolderName>, in this example
 * <ItemAdapter.ItemHolder>. This will enforce you to define a constructor for the
 * ItemAdapter and an inner class ItemsHolder (a static class)
 * The ItemsHolder class must extend RecyclerView.ViewHolder. In the constructor of this class,
 * you do something similar to the onCreate() method in an Activity.
 *
 * @author Lily Chang
 */
class ToppingsAdapter extends RecyclerView.Adapter<ToppingsAdapter.ToppingsHolder> {
    private static final int MAX_SELECTION = 7;
    private Context context; //need the context to inflate the layout
    private final ArrayList<Topping> toppings; //need the data binding to each row of RecyclerView
    private boolean isBYOSelected;  // Flag to control selection

    Listener listener;
    Singleton singleton = Singleton.getInstance();
    private Map<Topping, Boolean> toppingSelectionMap; //use this to keep track of if the topping is selected
    private static final Map<Topping, Integer> TOPPING_IMAGE_MAP = new HashMap<>();

    public ToppingsAdapter(Context context, ArrayList<Topping> toppings,
                           ArrayList<Topping> preselectedToppings,
                           boolean isBYOSelected,
                           Listener listener) { //TODO: add a parameter for preselected toppings?
        this.context = context;
        this.toppings = toppings;
        this.isBYOSelected = isBYOSelected;
        this.listener = listener;

        initToppingMap();

        toppingSelectionMap = new EnumMap<>(Topping.class);
        for (Topping topping : toppings) {
            if (preselectedToppings.contains(topping))
                toppingSelectionMap.put(topping, true);
            else
                toppingSelectionMap.put(topping, false);
        }
    }

    private void initToppingMap(){
        TOPPING_IMAGE_MAP.put(Topping.ANCHOVY, R.drawable.anchovy);
        TOPPING_IMAGE_MAP.put(Topping.BBQCHICKEN, R.drawable.bbqchicken);
        TOPPING_IMAGE_MAP.put(Topping.BEEF, R.drawable.beef);
        TOPPING_IMAGE_MAP.put(Topping.CHEDDAR, R.drawable.cheddar);
        TOPPING_IMAGE_MAP.put(Topping.GREENPEPPER, R.drawable.greenpepper);
        TOPPING_IMAGE_MAP.put(Topping.HAM, R.drawable.ham);
        TOPPING_IMAGE_MAP.put(Topping.MUSHROOM, R.drawable.mushroom);
        TOPPING_IMAGE_MAP.put(Topping.OLIVE, R.drawable.olive);
        TOPPING_IMAGE_MAP.put(Topping.ONION, R.drawable.onion);
        TOPPING_IMAGE_MAP.put(Topping.PEPPERONI, R.drawable.pepperoni);
        TOPPING_IMAGE_MAP.put(Topping.PINEAPPLE, R.drawable.pineapple);
        TOPPING_IMAGE_MAP.put(Topping.PROVOLONE, R.drawable.provolone);
        TOPPING_IMAGE_MAP.put(Topping.SAUSAGE, R.drawable.sausage);
    }

    public void setSelectedToppings(ArrayList<Topping> selectedToppings) {
        for (Topping topping : toppings) {
            if (selectedToppings.contains(topping))
                toppingSelectionMap.put(topping, true);
            else
                toppingSelectionMap.put(topping, false);
        }
    }

    public void setBYOSelected(boolean BYOSelected) {
        isBYOSelected = BYOSelected;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     *
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
     *
     * @param holder   the instance of ItemsHolder
     * @param position the index of the item in the list of items
     */
    @Override
    public void onBindViewHolder(@NonNull ToppingsHolder holder, int position) {
        Topping topping = toppings.get(position);
        holder.bind(topping);
        holder.textView.setText(toppings.get(position).name());

        holder.checkBox.setEnabled(isBYOSelected);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(Boolean.TRUE.equals(toppingSelectionMap.get(topping)));

        if (isBYOSelected) {
            itemViewClickListener(holder, topping);
            checkBoxClickListener(holder, topping);
        } else {
            //disable if not byo
            holder.itemView.setOnClickListener(null);
            holder.checkBox.setEnabled(false);
        }
    }

    private void itemViewClickListener(@NonNull ToppingsHolder holder, Topping topping) {
        holder.itemView.setOnClickListener(v -> {
            boolean newCheckedState = !holder.checkBox.isChecked();
            holder.checkBox.setChecked(newCheckedState);

            if (newCheckedState && getNumToppingsSelected() >= MAX_SELECTION) {
                holder.checkBox.setChecked(false);
            } else if (!newCheckedState && getNumToppingsSelected() <= MAX_SELECTION) {
                singleton.removeTopping(topping);
                toppingSelectionMap.put(topping, false);
            } else if (newCheckedState) {
                singleton.addTopping(topping);
                toppingSelectionMap.put(topping, true);
            }
        });
    }

    private void checkBoxClickListener(@NonNull ToppingsHolder holder, Topping topping) {
        holder.checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked && getNumToppingsSelected() >= MAX_SELECTION) {
                buttonView.setChecked(false);
            } else if (!isChecked && getNumToppingsSelected() <= MAX_SELECTION) {
                singleton.removeTopping(topping);
                toppingSelectionMap.put(topping, false);
            } else if (isChecked) {
                singleton.addTopping(topping);
                toppingSelectionMap.put(topping, isChecked);
            }
        }));
    }

    private int getNumToppingsSelected() {
        int num = 0;
        for (boolean isSelected : toppingSelectionMap.values()) {
            if (isSelected) num++;
        }
        return num;
    }

    /**
     * Get the number of items in the ArrayList.
     *
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
            imageView = itemView.findViewById(R.id.imageView4); //id is just named this its whatever
        }

        public void bind(Topping topping) {
            // Set the image based on the enum value
            int imageResId = TOPPING_IMAGE_MAP.get(topping);
            imageView.setImageResource(imageResId);

            // Set the text based on the enum name
            textView.setText(topping.name());
        }
    }

    public static interface Listener{
        public void onRVClick(String aParamToIdWhatWasClicked);
    }
}

