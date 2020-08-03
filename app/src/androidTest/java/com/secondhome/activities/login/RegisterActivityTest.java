package com.secondhome.activities.login;

import android.view.View;

import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;


import com.secondhome.R;
import com.secondhome.data.model.request.RegisterRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
@SmallTest
public class RegisterActivityTest {
    @Rule
    public ActivityTestRule<RegisterActivity> mActivityRule =
            new ActivityTestRule<RegisterActivity>(RegisterActivity.class);

    private RegisterActivity registerActivity=null;
    @Before
    public void setUp() throws Exception{

        registerActivity= mActivityRule.getActivity();
    }
    @Test
    public void register(){
        View view=registerActivity.findViewById(R.id.emailRegister);
        assertNotNull(view);
    }
    @Test
    public void whenPasswordTooShortReturnNeg1(){
        assertEquals(-1,registerActivity.areValid(new RegisterRequest("addff","asdrr","ssee","gg")));

    }
    @Test
    public void whenCredentialsTooShortReturnNeg2(){
        assertEquals(-2,registerActivity.areValid(new RegisterRequest("","asdrr","ssee","gggggg")));
    }
    @After
    public void tearDown(){
        registerActivity=null;
    }
}