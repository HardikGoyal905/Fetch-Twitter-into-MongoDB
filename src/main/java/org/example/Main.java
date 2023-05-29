package org.example;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ArrayList<String> cred = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader(new File("src/main/resources/Cred.txt"));
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                cred.add(line);
            }
        }
        catch(Exception ex){
            System.out.println("Credentials File not able to open");
            return;
        }

        String consumerKey=cred.get(0);
        String consumerSecret=cred.get(1);
        String accessToken=cred.get(2);
        String accessTokenSecret=cred.get(3);
        Twitter twitter=new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);

        List<TwitterProfile> friends = twitter.friendOperations().getFriends(System.getProperty("profile"));
        ListIterator<TwitterProfile> it=friends.listIterator();

        String connecString=System.getProperty("mongodb.uri");

        try(MongoClient client=MongoClients.create(connecString)){
            MongoDatabase database=client.getDatabase("Twitter");
            String following=System.getProperty("profile")+"Following";
            database.createCollection(following);
            MongoCollection<Document> collection=client.getDatabase("Twitter").getCollection(following);

            while(it.hasNext()){
                TwitterProfile friend=it.next();

                Document doc=new Document("_id", new ObjectId())
                        .append("Name", friend.getName())
                        .append("UserName", friend.getScreenName())
                        .append("Description", friend.getDescription())
                        .append("Following", friend.getFriendsCount())
                        .append("Followers", friend.getFollowersCount());


                collection.insertOne(doc);
            }
            client.close();
        }
        catch(Exception ex){
            System.out.println("Some error has been faced");
            return;
        }
    }
}