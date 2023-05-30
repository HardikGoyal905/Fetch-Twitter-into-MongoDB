package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.social.twitter.api.TwitterProfile;
import java.util.List;
import java.util.ListIterator;
import com.mongodb.client.model.Aggregates;
import org.bson.conversions.Bson;
import com.mongodb.client.model.*;
import java.util.Arrays;
public class MongoConnect {
    MongoClient client=null;
    MongoCollection<Document> collection=null;

    public void retrieveFamousFriends(){
        Bson matchStage=Aggregates.match(Filters.eq("User", System.getProperty("profile")));
        Bson sortStage=Aggregates.sort(Sorts.orderBy(Sorts.descending("Followers")));

        collection.aggregate(Arrays.asList(matchStage, sortStage)).forEach(document -> System.out.println(document.getString("Name")));
    }

    public void storeFriends(List<TwitterProfile> friends){
        ListIterator<TwitterProfile> it=friends.listIterator();
        while(it.hasNext()){
            TwitterProfile friend=it.next();

            Document doc=new Document("_id", new ObjectId())
                    .append("User", System.getProperty("profile"))
                    .append("Name", friend.getName())
                    .append("UserName", friend.getScreenName())
                    .append("Description", friend.getDescription())
                    .append("Following", friend.getFriendsCount())
                    .append("Followers", friend.getFollowersCount());


            collection.insertOne(doc);
        }
    }

    public void close(){
        client.close();
    }

    public MongoConnect(){
        try{
            client=MongoClients.create(System.getProperty("mongodb.uri"));
            MongoDatabase database=client.getDatabase("Twitter");
            collection=database.getCollection("Friends");
        }
        catch(Exception ex){
            System.out.println("Some error has been faced");
            return;
        }
    }
}
