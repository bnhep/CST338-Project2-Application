package com.example.project2.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.platform.app.InstrumentationRegistry;


import org.junit.Before;
import org.junit.Test;

public class AccountStatusCheckTest {

    private AccountStatusCheck accountManager;
    private SharedPreferences prefs;


    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AccountStatusCheck.init(appContext);
        accountManager = AccountStatusCheck.getInstance();
        prefs = appContext.getSharedPreferences("com.example.project2.PREF_FILE_NAME", Context.MODE_PRIVATE);
        accountManager.logout();
    }

    @Test
    public void testSetIDAndGetID(){
        int testID = 12;
        accountManager.setUserID(testID);
        int getUserID = accountManager.getUserID();
        assertNotEquals(14, getUserID);
        assertEquals(testID, getUserID);
    }

    @Test
    public void testSetAndGetUsername() {
        String testUser = "testUser1";
        accountManager.setUserName(testUser);
        String getUser = accountManager.getUserName();
        assertNotEquals("notTestUSer", getUser);
        assertEquals(testUser, getUser);
    }

    @Test
    public void testSetandGetAdminStatus(){
        boolean isAdmin = true;
        accountManager.setIsAdminStatus(isAdmin);
        assertNotEquals(false,accountManager.getIsAdminStatus());
        assertTrue(accountManager.getIsAdminStatus());

    }

    @Test
    public void testLogout(){
        accountManager.setUserID(12);
        assertTrue(prefs.contains("com.example.project2.ACCOUNT_USER_ID"));
        accountManager.logout();
        int userLoggedOut = -1;
        assertEquals(userLoggedOut, accountManager.getUserID());

    }
}
