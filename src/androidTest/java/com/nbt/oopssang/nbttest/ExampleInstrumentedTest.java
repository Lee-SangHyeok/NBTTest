package com.nbt.oopssang.nbttest;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.nbt.oopssang.nbttest", appContext.getPackageName());
    }

    @Test
    public void webViewTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(appContext,WebViewActivity.class);
        intent.putExtra(WebViewActivity.TIMES_URL, "www.naver.com");
        appContext.startActivity(intent);
        assertEquals("com.nbt.oopssang.nbttest", appContext.getPackageName());
    }

}
