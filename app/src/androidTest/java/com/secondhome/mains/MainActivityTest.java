package com.secondhome.mains;

import android.view.View;

import androidx.test.annotation.UiThreadTest;
import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.secondhome.R;
import com.secondhome.login.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4ClassRunner.class)
@SmallTest
public class MainActivityTest {

        @Rule
        public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

        private MainActivity mainActivity=null;
        @Before
        public void setUp() throws Exception{

                mainActivity= mActivityRule.getActivity();
        }
        @Test
        public void testMain(){
            View view = mainActivity.findViewById(R.id.mainmenu);
            assertNotNull(view);
            System.out.println("Test1");

        }


        @After
        public void tearDown(){
            mainActivity=null;
        }



}