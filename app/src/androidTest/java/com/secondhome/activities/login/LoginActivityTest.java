package com.secondhome.activities.login;

import android.view.View;

import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.secondhome.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4ClassRunner.class)
@SmallTest
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    private LoginActivity loginActivity=null;
    @Before
    public void setUp() throws Exception{

        loginActivity= mActivityRule.getActivity();
    }
    @Test
    public void testLogin(){
        View view = loginActivity.findViewById(R.id.email);
        assertNotNull(view);
        System.out.println("Test1");

    }


    @After
    public void tearDown(){
        loginActivity=null;
    }

}