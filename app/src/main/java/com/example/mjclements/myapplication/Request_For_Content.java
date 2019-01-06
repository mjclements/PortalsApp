package com.example.mjclements.myapplication;
import java.util.List;
import java.util.UUID;

public class Request_For_Content {
    public List_of_Scene Liked;
    public List_of_Scene Seen;
    public List<String> tags;

    public Request_For_Content(List_of_Scene Liked, List_of_Scene Seen, List<String> tags) {
        this.Liked = Liked;
        this.Seen = Seen;
        this.tags = tags;
    }
    public Request_For_Content( )
    {

    }

}
