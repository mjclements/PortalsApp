package com.example.mjclements.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Collections;
import java.util.Comparator;
import java.sql.Timestamp;

public class List_of_Scene {
    public List<Scene> list_of_seen;
    int time;

    List_of_Scene(){
        this.list_of_seen = new ArrayList<Scene>();
        this.time = 0;
    }

    void add_scene(Scene to_add){
        this.list_of_seen.add(to_add);
    }

    public void display(){
        // Stub
    }

    /**
     *
     * @param to_remove The Scene which needs to be forgotten from the list
     */
    void delete_scene(Scene to_remove){
        this.list_of_seen.remove(to_remove);
        to_remove.delete_scene();
    }
    public int size()
    {
        return list_of_seen.size();
    }
    /**
     * Sorts the fields by their Time value
     */
    void sort_by_time_spent(){
        //       this.list_of_seen.sort(java.util.Comparator.comparingInt(Scene.time);
    }

    /**
     * Sorts the firelds by their Timestamp value
     */
    void sort_by_time_added(){
//        this.list_of_seen.sort(java.util.Comparator.comparingInt(Scene.timestamp));
    }
    List<Scene> top_scenes(){
        //Creates a list<scene> for sending a request_4_content
        return new ArrayList<Scene>();
    }

    /**
     *
     * @return a list of UUID's associated with this List_of_Scene
     */
    List<UUID> report_seen(){
        List<UUID> report = new ArrayList<UUID>();
        for(Scene s : this.list_of_seen){
            report.add(0, s.ID);
        }
        return report;
    }

    /**
     *
     * a sorted list of UUID's by date.
     */
    void sort_by_date(){

        for (int i = 0; i < size(); i++)
        {
            for(int j = 1; j < ( size() - i ); j++)
            {
                if( list_of_seen.get( j ).timestamp.compareTo( list_of_seen.get( j - 1 ).timestamp ) < 0 )
                {
                    Scene tempVar = list_of_seen.get(j);
                    list_of_seen.remove(tempVar);
                    list_of_seen.add(j -1,tempVar);
                }
            }
        }
    };
    /**
     *  Merges a list of scene into another list of scene
     * @param to_merge the list containing the stuff to add
     */
    void merge(List_of_Scene to_merge){
        this.list_of_seen.addAll(to_merge.list_of_seen);
    }

}
