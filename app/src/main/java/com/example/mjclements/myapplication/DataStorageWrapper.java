package com.example.mjclements.myapplication;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.ContextWrapper;
import android.content.Context;


import java.io.File;
import java.net.URL;
import java.security.AccessControlContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.security.AccessController.getContext;

public class DataStorageWrapper {

    private static DataStorageWrapper instance = null;

    public SQLiteDatabase db;


    public DataStorageWrapper(Context context){
        if (instance == null) {
            File file = new File("portalz.db");
            String path = context.getApplicationContext().getFilesDir().getAbsolutePath().concat("/portals.db");
            Log.d("CUCK ME ", path);
            this.db = SQLiteDatabase.openOrCreateDatabase(path, null, null);
        } else {

        }
    }
    /**
     * Checks if the database exists
     */
    public boolean check_if_Exists() {
        return true;
        //TODO fix this
    }
    /**
     * Executes the SQL command for initializing the User Table
     */
    public void create_User_Table(){
        db.execSQL("CREATE TABLE IF NOT EXISTS main.Users (" +
                "ID STRING PRIMARY KEY," +
                "Name STRING NOT NULL," +
                "Email STRING," +
                "Pass BINARY(64) NOT NULL);");
    }

    /**
     * Executes the SQL command for initializing the Profile Table
     */
    public void create_Profile_Table(){
        db.execSQL("CREATE TABLE IF NOT EXISTS main.Profiles (" +
                "ID STRING PRIMARY KEY," +
                "Name STRING, " +
                "Show BOOL NOT NULL," +
                "Learn BOOL NOT NULL," +
                "Tell BOOL NOT NULL," +
                "User_ID STRING NOT NULL," +
                "Netflix BOOL NOT NULL," +
                "Spotify BOOL NOT NULL," +
                "YouTube BOOL NOT NULL);");
    }
    /**
     * Executes the SQL command for initializing the Scene Table
     */
    public void create_Scene_Table(){
        db.execSQL("CREATE TABLE IF NOT EXISTS main.Scenes (" +
                "ID STRING, " +
                "Time INTEGER, " +
                "Timestamp TIMESTAMP, " +
                "Liked BOOL, " +
                "Profile STRING);");
    }
    /**
     * Executes the SQL command for initializing the Tag Table
     */
    public void create_Tag_Table(){
        db.execSQL("CREATE TABLE IF NOT EXISTS main.Tags (" +
                "ID STRING PRIMARY KEY, " +
                "Text String, " +
                "Profile STRING);");
    }
    public void initiate(){
        this.create_Tag_Table();
        this.create_User_Table();
        this.create_Profile_Table();
        this.create_Scene_Table();
    }

    /**
     * Inserts a User object into the database as a bunch of entries in tables
     * @param to_add The user who is going to be added
     * @param Pass the string-form password
     */
    public void insert_User(User to_add, String Pass){
        ContentValues Content = new ContentValues();
        Content.put("ID",  to_add.ID.toString());
        Content.put("'Name'", to_add.name);
        Content.put("'Pass'", Pass);
        Content.put("Email", to_add.email);
//         @TODO DONT STORE PASSWORDS IN PLAINTEXT YOU BITCH
        db.insert("main.Users", null, Content);
        for(Profile p : to_add.list_of_profiles){
            insert_Profile(to_add, p);

        }
    }

    /**
     * Inserts the Profile in such a way that it will be accessible by the user in the future
     * @param user The user who will access
     * @param to_add The profile which will be accessed
     */
    public void insert_Profile(User user, Profile to_add){
        ContentValues Content = new ContentValues();
        Content.put("ID", to_add.ID.toString());
        Content.put("'Show'", to_add.show_splash);
        Content.put("'Learn'", to_add.learn);
        Content.put("'Tell'", to_add.tell);
        Content.put("Name", to_add.name);
        Content.put("User_ID", user.ID.toString());
        Content.put("Netflix", to_add.Netflix);
        Content.put("Spotify", to_add.Spotfy);
        Content.put("YouTube", to_add.Youtube);
        db.insert("main.Profiles", null, Content);
        for(Scene s : to_add.Liked.list_of_seen){
            insert_Scene(to_add, s);
        }
        for(Scene q : to_add.Seen.list_of_seen){
            insert_Scene(to_add, q);
        }
        for(String t : to_add.tags){
            insert_Tag(to_add, t);
        }
    }

    /**
     * Inserts a scene in such a way that it can be acessed by the profile
     * @param viewer The Profile which will be able to get at the Scene
     * @param viewed The Scene which the Profile will get at
     */
    public void insert_Scene(Profile viewer, Scene viewed){
        ContentValues Content = new ContentValues();
        Content.put("ID", viewed.ID.toString());
        Content.put("Time", viewed.time);
        Content.put("Timestamp", viewed.timestamp.toString());
        Content.put("Liked", viewed.liked);
        Content.put("Profile", viewer.ID.toString());
        db.insert("main.Scenes", null, Content);
    }

    /**
     * Inserts the tag in such a way that the PRofile will be able to get at it
     * @param tager The Profile which possess the tags
     * @param tag The tag which is possessed
     */
    public void insert_Tag(Profile tager, String tag){
        ContentValues Content = new ContentValues();
        Content.put("'ID'", tager.ID.toString());
        Content.put("'Text'", tag);
        Content.put("'Profile'", tager.ID.toString());
        db.insert("main.Tags", null, Content);

    }

    /**
     * Retrieves a User from the database based upon their UUID (Will be DEPRECATED) when
     * authentication becomes more widely used throughout the service
     * @param ID The ID of the user to be acessed
     * @return The User, as a java object
     */
    public User get_User(UUID ID) {
        String table = "main.Users";
        String[] columns = new String[4];
        columns[0] = "ID";
        columns[1] = "Name";
        columns[2] = "Pass";
        columns[3] = "Email";
        String selection = "ID='".concat(ID.toString()) + "'";
        Cursor result = this.db.query(table, columns, selection, null, null, null, null, null);
        result.moveToFirst();
        User accessed = new User(result.getString(result.getColumnIndex("Name")));
        accessed.ID = UUID.fromString(result.getString(0));
        accessed.list_of_profiles = this.get_Profiles(accessed);
        accessed.email = result.getString(3);
        result.close();
        return accessed;
    }

    /**
     * Lets a profile go and get its list of scene
     * @param portal The portal which needs to get its list
     * @return a List of the Profile's scenes
     */
    public List<Scene> get_Scenes(Profile portal) {
        String table = "main.Scenes";
        String[] columns = new String[5];
        columns[0] = "ID";
        columns[1] = "Time";
        columns[2] = "Timestamp";
        columns[3] = "Liked";
        columns[4] = "Profile";
        String selection = "Profile='" + portal.ID.toString() + "'";
        List<Scene> los = new ArrayList<Scene>();
        Cursor result = this.db.query(table, columns, selection, null, null, null, null, null);
        result.moveToFirst();
        while(!result.isAfterLast()){
            Scene s = new Scene(result.getInt(1), (result.getInt(3) > 0) );
            s.ID.fromString(result.getString(0));
            s.timestamp = Timestamp.valueOf(result.getString(2));
            los.add(s);
            result.move(1);
        }
        return los;
    }

    public List<Profile> get_Profiles(User user){
        String table = "main.Profiles";
        String[] columns = new String[9];
        columns[0] = "ID";
        columns[1] = "Show";
        columns[2] = "Learn";
        columns[3] = "Tell";
        columns[4] = "Name";
        columns[5] = "User_ID";
        columns[6] = "Netflix";
        columns[7] = "Spotify";
        columns[8] = "Youtube";
        String selection = "User_ID='" + user.ID.toString() + "'";
        List<Profile> lop = new ArrayList<Profile>();
        Cursor result = this.db.query(table, columns, selection, null, null, null, null, null);
        result.moveToFirst();
        while(!result.isAfterLast()) {
            Profile p = new Profile(result.getString(4));
            p.Liked = new List_of_Scene();
            p.Liked.list_of_seen.addAll(this.get_Scenes(p));
            p.ID.fromString(result.getString(0));
            p.show_splash = result.getInt(1) > 0;
            Log.d("FUCK", result.getString(1));
            p.learn = result.getInt(2) > 0;
            p.tell = result.getInt(3) > 0;
            p.Netflix = result.getInt(6) > 0;
            p.Spotfy = result.getInt(7) > 0;
            p.Youtube = result.getInt(8) > 0;
            lop.add(p);
            result.move(1);
        }
        return lop;
    }

    public User basic_authenticate(String email, String pass) throws Exception {
        String table = "main.Users";
        String[] columns = new String[4];
        columns[0] = "ID";
        columns[1] = "Name";
        columns[2] = "Pass";
        columns[3] = "Email";
        String selection = "Email='" + email + "'";
        Cursor result = this.db.query(table, columns, selection, null, null, null, null, null);
        if(result.getCount() == 0) {
            throw new Exception("No User Found! ;)");
        } else {
            result.moveToFirst();
            if(true) {
                User accessed = new User(result.getString(result.getColumnIndex("Name")));
                accessed.ID = UUID.fromString(result.getString(0));
                accessed.list_of_profiles = this.get_Profiles(accessed);
                accessed.email = result.getString(3);
                result.close();
                return accessed;
            } else {
                throw new Exception("No User Found! ;)");
                }
        }
    }

    public void update_User(User user){
//TODO Make this function
    }
    public void close(){
        this.db.close();
    }

    public void dump_database(){
        this.db.execSQL("DROP TABLE IF EXISTS main.Users");
        this.db.execSQL("DROP TABLE IF EXISTS main.Tags");
        this.db.execSQL("DROP TABLE IF EXISTS main.Profiles");
        this.db.execSQL("DROP TABLE IF EXISTS main.Scenes");

    }

//TODO TEST
    /**
     * Updates a User's core data, not its list of Profiles
     * @param to_update The Update User. The ID determines which existing user this is.
     * @param Pass The password (hashed, eventually) for the user TODO Hash this shit.
     */
    public void update_User(User to_update, String Pass){
        ContentValues Content = new ContentValues();
        Content.put("ID",  to_update.ID.toString());
        Content.put("'Name'", to_update.name);
        Content.put("'Pass'", Pass);
        Content.put("Email", to_update.email);
        String selection = "ID='?'";
        String[] Args = new String[1];
        Args[0] = to_update.ID.toString();
//         @TODO DONT STORE PASSWORDS IN PLAINTEXT YOU BITCH
        db.update("main.Users",  Content, selection, Args);

    }

///TODO Test
    public void update_Profile(Profile to_update){
        ContentValues Content = new ContentValues();
        Content.put("'Show'", to_update.show_splash);
        Content.put("'Learn'", to_update.learn);
        Content.put("'Tell'", to_update.tell);
        Content.put("Name", to_update.name);
        String selection = "ID='?'";
        String[] Args = new String[1];
        Args[0] = to_update.ID.toString();
        this.db.update("main.Profiles", Content, selection, Args);
    }
///TODO Test
    public void update_Scene(Scene to_update, Profile profile) {
        ContentValues Content = new ContentValues();
        Content.put("Time", to_update.time);
        Content.put("Timestamp", to_update.timestamp.toString());
        Content.put("Liked", to_update.liked);
        String selection = "ID='?' AND Profile_ID='?'";
        String[] Args = new String[2];
        Args[0] = to_update.ID.toString();
        Args[1] = profile.ID.toString();
        this.db.update("main.Scene", Content, selection, Args);
    }
}
