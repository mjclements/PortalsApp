package com.example.mjclements.myapplication;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Profile {
    public UUID ID;
    public String name;
    //way to hold an image
    public List_of_Scene Liked;
    public List_of_Scene Seen;
    public List<String> tags;
    //   public List<Media_Type> preference; this will be handled at a later time
    public boolean show_splash;
    public boolean learn;
    public boolean tell;
    public boolean Netflix;
    public boolean Spotfy;
    public boolean Youtube;

    public Profile(String name, boolean show_splash, boolean learn, boolean tell, boolean Netflix, boolean Spotify, boolean Youtube){
        this.name = name;
        this.show_splash = show_splash;
        this.learn = learn;
        this.tell = tell;
        this.Netflix = Netflix;
        this.Spotfy = Spotify;
        this.Youtube = Youtube;
    }

    public Profile(String name){
        this.ID = UUID.randomUUID();
        this.name = name;
        this.Liked = new List_of_Scene();
        this.Seen = new List_of_Scene();
        this.tags = new ArrayList<String>();
        this.show_splash = true;
        this.learn = true;
        this.tell = true;
        this.Netflix = true;
        this.Spotfy = true;
        this.Youtube = true;
    }

    /**
     * Takes a profile and returns a perfect copy with a new ID
     * @return the copied profile
     */
    public Profile copy(){
        Profile copy = new Profile(this.name.concat("copy"));
        copy.ID = UUID.randomUUID();
        copy.name = name;
        copy.Liked = Liked;
        copy.Seen = Seen;
        copy.tags = tags;
        //       copy.preference = preference;
        copy.show_splash = show_splash;
        copy.learn = learn;
        copy.tell = tell;
        return copy;
    }
    public String getName()
    {
        return this.name;
    }
    Request_For_Content generate_request_for_content(){
        return new Request_For_Content(this.Liked, this.Seen, this.tags);
    }
    void delete(){

    }

    void branch(){
        //give you a whole-ass menu to create a branch
    }

    /**
     *  Combines the calling profile and a target profile to make one, cohesive merged profile
     * @param to_combine The secondary profile to subsume into merger profile
     * @return The finalised merged profile
     */
    Profile merge(Profile to_combine){
        Profile merger = new Profile(this.name + to_combine.name);
        merger.ID = UUID.randomUUID();
        // Merge the Liked
        merger.Liked = new List_of_Scene();
        merger.Liked.merge(this.Liked);
        merger.Liked.merge(to_combine.Liked);
        //Merge the Seen
        merger.Seen = new List_of_Scene();
        merger.Seen.merge(this.Seen);
        merger.Seen.merge(to_combine.Seen);

        merger.tags = new ArrayList<String>();
        merger.tags.addAll(this.tags);
        merger.tags.addAll(to_combine.tags);

        merger.show_splash = this.show_splash;
        merger.learn = this.learn;
        merger.tell = this.tell;

        return merger;
    }
    public String Create_Liked_Tables(){
        String query = "CREATE TABLE 'main.Liked_";
        query += this.ID.toString();
        query += "' (";
        query += "ID INTEGER NOT NULL, ";
        query += "Time INTEGER NOT NULL, ";
        query += "Liked BOOL, ";
        query += "Timestamp DATETIME";
        query += ");";
        return query;
    }

    public String Create_Seen_Tables(){
        String query = "CREATE TABLE 'main.Seen_";
        query += this.ID.toString();
        query += "' (";
        query += "ID INTEGER NOT NULL, ";
        query += "Time INTEGER NOT NULL, ";
        query += "Liked BOOL, ";
        query += "Timestamp DATETIME";
        query += ");";
        return query;
    }

    public String Create_Tag_Tables(){
        String query = "CREATE TABLE 'main.Tags_";
        query += this.ID.toString();
        query += "' (";
        query += "Tags STRING";
        query += ");";
        return query;
    }

    public void import_List(List<Scene> scenes){
        for(Scene s : scenes){
            if(s.liked){
                this.Liked.add_scene(s);
            } else {
                this.Seen.add_scene(s);
            }
        }
    }

    public List<Scene> export_List(){
        List<Scene> los = new ArrayList<Scene>();
        for(Scene s : this.Liked.list_of_seen){
            los.add(s);
        }
        for(Scene s : this.Seen.list_of_seen){
            los.add(s);
        }
        return los;
    }
}
