package org.example;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ListIterator;
import org.bson.types.ObjectId;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoDatabase;
import org.springframework.social.twitter.api.CursoredList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.


public class Main {
    public static BufferedReader reader(){
        return new BufferedReader(new InputStreamReader(System.in));
    }

    public static int askFunc(){
        System.out.println("Which type of Functionality you want:");
        System.out.println("1. Store Friends");
        System.out.println("2. Expose The Friends");


        try {
            return Integer.parseInt(reader().readLine());
        }
        catch(Exception ex){
            System.out.println("Enter the valid request");
            return 0;
        }
    }


    public static void main(String[] args) {
        TwitConnect twitConnect=new TwitConnect();

        MongoConnect mongoConnect=new MongoConnect();

        int task=askFunc();
        if(task==1){
            List<TwitterProfile> friends=twitConnect.fetchFriends();
            mongoConnect.storeFriends(friends);
        }
        else{
            mongoConnect.retrieveFamousFriends();
        }

        mongoConnect.close();

    }
}