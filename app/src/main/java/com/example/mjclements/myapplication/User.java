package com.example.mjclements.myapplication;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.database.DatabaseUtils.*;

public class User {
    public String name;
    List<Profile> list_of_profiles;
    public UUID ID;
    String email;
    UUID Profile_Table;

    public User(String name) {
        this.name = name;
        this.ID = UUID.randomUUID();
        this.list_of_profiles = new ArrayList<Profile>();
    }
    public User(String name, String email){
        this.name = name;
        this.ID = UUID.randomUUID();
        this.list_of_profiles = new ArrayList<Profile>();
        this.email = email;
    }

    public void add_Profile(Profile to_add){
        this.list_of_profiles.add(to_add);
    }

    public List<Profile> get_Profile(){
        return this.list_of_profiles;
    }

    /**
     *
     */
    void branch_profile(Profile to_branch) {
        this.list_of_profiles.add(to_branch.copy());
    }

    /**
     * delete_profile
     * Removes the parameter
     *
     * @param ID
     */
    void delete_profile(UUID ID) {
        for (Profile p : list_of_profiles) {
            if (p.ID.equals(ID)) {
                list_of_profiles.remove(p);
            }
        }
    }
    /**
     * Access UUID for this user
     *
     *
     *
     * @return The UUID
     */
    public UUID getID()
    {
        return this.ID;
    }
    /**
     * Generates the string necessary to insert this user
     * into the persistent database via SQL
     *
     * @param Pass The User's properly hashed password @TODO Figure out how to do that
     * @return The string, which must then be sent to the DB via some query function
     */
    public String Insert_User(String Pass) {
        String query = new String();
        query += "INSERT INTO 'main.Users' ( ";
        query += "ID, Name, Pass, Profiles_ID)";
        query += " VALUES( ";
        query += this.ID.toString();
        query += ", ";
        query += this.name;
        query += ", ";
        query += Pass;
        query += ", ";
        query += this.Profile_Table.toString();
        query += ");";
        return query;
    }

    public String Create_Profile_Table() {
        String query = "CREATE TABLE 'main.Profile_";
        query += this.ID;
        query += "' (ID STRING PRIMARY KEY," +
                "show_splash BOOL, " +
                "Learn BOOL, " +
                "Tell BOOL" +
                ");";
        return query;
    }
    public void Create_Profile_Storage() {
//@TODO Find a way to write to persistent storage (between runs, not between screens)
    }
    public String Insert_Profile_To_DB(Profile to_add){
        String query = "INSERT INTO 'main.Profile_";
                query += this.ID;
        query+= "' (ID, show_splash, Learn, Tell) VALUES ('" +
                to_add.ID.toString() +
                "', '" +
                to_add.show_splash +
                "', '" +
                to_add.learn +
                "', '" +
                to_add.tell +
                "');";
        return query;

    }
}