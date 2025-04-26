package com.example.project2.viewholders;

import com.example.project2.database.entities.User;

/**
 * Interface for the new click method implemented in the ViewUsersActivity, click is activated
 * in the UserViewAdapter.
 * @author Brandon Nhep
 * Date: 4/21/25
 */
public interface UserSelectListener {

    void onUserClicked(User user);
}
