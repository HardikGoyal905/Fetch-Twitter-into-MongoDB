package org.example;

import org.springframework.social.connect.UserProfile;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TwitConnect {
    TwitterTemplate twitter=null;

    private void readCred(ArrayList<String> cred){
        try {
            FileReader fileReader = new FileReader(new File("src/main/resources/Cred.txt"));
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                cred.add(line);
            }
        } catch (Exception ex) {
            System.out.println("Credentials File not able to open");
        }
    }

    public List<TwitterProfile> fetchFriends(){
        return twitter.friendOperations().getFriends(System.getProperty("profile"));
    }

    public TwitConnect(){
        ArrayList<String> cred = new ArrayList<String>();
        readCred(cred);

        String consumerKey = cred.get(0);
        String consumerSecret = cred.get(1);
        String accessToken = cred.get(2);
        String accessTokenSecret = cred.get(3);

        twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}
