package com.example.mjclements.myapplication;

import android.app.Instrumentation;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.List;

import static android.database.sqlite.SQLiteDatabase.CREATE_IF_NECESSARY;
import static android.support.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseWrapperTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.mjclements.myapplication", appContext.getPackageName());
    }
    @Test
    public void Profile_Table_Quantity_Test(){
    Context context = getContext();
    DataStorageWrapper db = new DataStorageWrapper(InstrumentationRegistry.getTargetContext());
//    db.db.execSQL("DROP TABLE 'main.Profiles'");
//    db.db.execSQL("DROP TABLE 'main.Users'");
    db.create_User_Table();
    db.create_Profile_Table();
    db.create_Tag_Table();
    db.create_Scene_Table();
    User dummy = new User("Mike");
    Profile prof1 = new Profile("Prof1");
    Profile prof2 = new Profile("Prof2");
    Scene scene1 = new Scene(5, true);
    Scene scene2 = new Scene(5, false);
    Scene scene3 = new Scene(10, true);
    prof1.Liked.add_scene(scene1);
    prof2.Liked.add_scene(scene2);
    prof1.Liked.add_scene(scene3);
    dummy.list_of_profiles.add(prof1);
    dummy.list_of_profiles.add(prof2);
    db.insert_User(dummy, "Cuckkkk");
    DataStorageWrapper ac = new DataStorageWrapper(InstrumentationRegistry.getTargetContext());
    User dummy_2 = ac.get_User(dummy.ID);
    assertEquals(dummy.name, dummy_2.name);
    assertEquals(dummy.ID, dummy_2.ID);
    assertEquals("Prof1", dummy_2.list_of_profiles.get(0).name);
    assertEquals("Prof2", dummy_2.list_of_profiles.get(1).name);
    assertEquals(2, db.get_Profiles(dummy).size());
    assertEquals(2, db.get_Scenes(prof1).size());
    }
    @Test
    public void Profile_Validity_Test(){
        DataStorageWrapper db = new DataStorageWrapper(InstrumentationRegistry.getTargetContext());
        db.dump_database();
        db.initiate();
        User guy = new User("String", "email@email.com");
        Profile prof1 = new Profile("Profile_One", true, false, false, false, false, true);
        guy.add_Profile(prof1);
        db.insert_User(guy, "Password123");
        List<Profile> retrieved = db.get_Profiles(guy);
        Profile to_Test = retrieved.get(0);
        assertTrue(to_Test.show_splash);
        assertFalse(to_Test.learn);
        assertFalse(to_Test.tell);
        assertFalse(to_Test.Netflix);
        assertFalse(to_Test.Spotfy);
        assertTrue(to_Test.Youtube);
    }
    public void basic_auth_test(){
        Context context = getContext();
        DataStorageWrapper db = new DataStorageWrapper(InstrumentationRegistry.getTargetContext());
        db.db.deleteDatabase(new File(":memory"));
        db.create_User_Table();
        db.create_Profile_Table();
        db.create_Tag_Table();
        db.create_Scene_Table();
        User test = new User("Fuffy", "Fuffy@fuffy.com");
        db.insert_User(test, "fufme123");
        User loaded = new User("NOTFUFFY");
        try {
            loaded = db.basic_authenticate("Fuffy@fuffy.com", "WRONG PASS");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotEquals(test.name, loaded.name);
        try {
            loaded = db.basic_authenticate("WRONG_EMAIL", "fufme123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotEquals(test.name, loaded.name);
        try {
            loaded = db.basic_authenticate("Fuffy@fuffy.com", "fufme123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(test.name, loaded.name);
        assertEquals(test.list_of_profiles.size(), loaded.list_of_profiles.size());

    }
    @Test
    public void update_Test(){
        DataStorageWrapper db = new DataStorageWrapper(InstrumentationRegistry.getTargetContext());
        User one = new User("Fuffer", "example@gmail.com");
        Profile prof1 = new Profile("Nazi");
        Profile prof2 = new Profile("Poet");

    }

}