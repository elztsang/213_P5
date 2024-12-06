package com.example.androidpizzaria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.EnumMap;

import pizzaria.Topping;

/**
 * An Adapter class to be used to instantiate an adapter for the RecyclerView of toppings that is used when creating a pizza.
 *
 * @author Lily Chang, Elizabeth Tsang, Ron Chrysler Amistad
 */
class ToppingsAdapter extends RecyclerView.Adapter<ToppingsAdapter.ToppingsHolder> {
    private static final int MAX_SELECTION = 7; //max number of toppings that can be selected
    private static final Map<Topping, Integer> TOPPING_IMAGE_MAP = new HashMap<>(); //maps a topping to its respective image
    private final ArrayList<Topping> toppings; //need the data binding to each row of RecyclerView
    private Context context; //need the context to inflate the layout
    private boolean isBYOSelected;  // Flag to control selection
    private Map<Topping, Boolean> toppingSelectionMap; //use this to keep track of if the topping is selected
    Listener listener;
    Singleton singleton = Singleton.getInstance();

    /**
     * Constructor for the adapter.
     *
     * @param context
     * @param toppings
     * @param preselectedToppings
     * @param isBYOSelected
     * @param listener
     */
    public ToppingsAdapter(Context context, ArrayList<Topping> toppings,
                           ArrayList<Topping> preselectedToppings,
                           boolean isBYOSelected,
                           Listener listener) {
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

    /**
     * Initializes the mapping of toppings to their respective images.
     */
    private void initToppingMap() {
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

    /**
     * Sets the toppings in the map to be selected according to the specified ArrayList of toppings.
     * If the topping exists in the specified toppings list, the value of the topping in the map is set to true, otherwise false.
     *
     * @param selectedToppings the list of toppings to set to selected in the toppings map.
     */
    public void setSelectedToppings(ArrayList<Topping> selectedToppings) {
        for (Topping topping : toppings) {
            if (selectedToppings.contains(topping))
                toppingSelectionMap.put(topping, true);
            else
                toppingSelectionMap.put(topping, false);
        }
    }

    /**
     * Sets the flag for enabling selection in the recycler view to the specified boolean.
     * If the pizza type selected in the recycler view is BYO, the flag will be true, otherwise false.
     *
     * @param BYOSelected the boolean to set the flag to.
     */
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

    /**
     * Helper method to initialize the click listener for recycler view items.
     *
     * @param holder  holder
     * @param topping topping
     */
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

            listener.onRVClick(topping.toString());
        });
    }

    /**
     * Helper method to initialize the listener for the checkboxes in the recycler view
     *
     * @param holder  holder
     * @param topping topping
     */
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

            listener.onRVClick(topping.toString());
        }));
    }

    /**
     * Helper method to get the number of toppings selected according to their boolean value in the map.
     *
     * @return number of toppings that are selected
     */
    private int getNumToppingsSelected() {
        int num = 0;
        for (boolean isSelected : toppingSelectionMap.values()) {
            if (isSelected) num++;
        }
        return num;
    }

    /**
     * Get the number of toppings in the ArrayList.
     *
     * @return the number of toppings in the list.
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

    /**
     * A recycler view interface to allow for callbacks
     */
    public static interface Listener {
        public void onRVClick(String aParamToIdWhatWasClicked);
    }
}

