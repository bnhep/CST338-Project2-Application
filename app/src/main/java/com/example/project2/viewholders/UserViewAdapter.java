package com.example.project2.viewholders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.database.entities.User;

import java.util.List;

/**
 * View adapter class for the recycler view to create holders and bind the data
 * @author Brandon Nhep
 * Date: 4/21/25
 */
public class UserViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

    Context context;
    private List<User> users;
    private UserSelectListener listener;

    /**
     * method to set the user's based off the passed in list of the database
     * @param users
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    /**
     * Constructor that takes in the context of application, the list of user's from the DAO,
     * and a listener to create a button
     * @param context
     * @param users
     * @param listener
     */
    public UserViewAdapter(Context context, List<User> users, UserSelectListener listener) {
        this.context = context;
        this.users = users;
        this.listener = listener;
    }

    /**
     * Returns the view holder class created with a layout inflater that inflates the view
     * created in the user recycler xml
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.user_recycler_item, parent, false));
    }

    /**
     * Takes the passed in user's username and password and set the text to the holder's text views
     * Creates a new button from the holder's username textview
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        User current = users.get(position);
        holder.usernameTextView.setText(current.getUsername());
        holder.passwordTextView.setText(current.getPassword());


        holder.usernameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClicked(users.get(position));
            }
        });
    }

    /**
     * Overrides the size of the recycler view display
     * @return the list size
     */
    @Override
    public int getItemCount() {
        return users.size();
    }





}
