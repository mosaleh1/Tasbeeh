package com.runcode.tasbee7;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.runcode.tasbee7.Azkar.Zikr;
import com.runcode.tasbee7.database.AzkarDao;
import com.runcode.tasbee7.database.AzkarDatabase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AzkarDatabaseTest {

    private AzkarDao userDao;
    private AzkarDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AzkarDatabase.class).build();
        userDao = db.getAzkarDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        List<Zikr> myList = userDao.getMorningAZkar();
        Assert.assertEquals(24, myList.size());
    }
}
