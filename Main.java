import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// NOTE: This program will not work if someones ig username contains "Follow" or "Remove"

class Main {
    public static void main(String[] args) {
        List<String> followers = readCSV("Followers M24 - Sheet1.csv");
        List<String> following = readCSV("Following M24 - Sheet1.csv");

        // Filter out non-usernames
        followers = filterFollowers(followers);
        following = filterFollowing(following);

        // Mutuals
        List<String> dawgs = new ArrayList<>();
        dawgs.addAll(followers);
        dawgs.retainAll(following);

        // Accounts you don't follow back
        List<String> disciples = new ArrayList<>();
        disciples.addAll(followers);
        disciples.removeAll(following);

        // OPPS 👹
        List<String> opps = new ArrayList<>();
        opps.addAll(following);
        opps.removeAll(followers);

        //System.out.println(followers);
        System.out.println(followers.size());
        //System.out.println(following);
        System.out.println(following.size());
        
        revealOpps(opps);
    }

    // filters Followers list for the user
    private static List<String> filterFollowersSelf(List<String> usernames) { // add Requested?
        List<String> newUsernames = new ArrayList<>();

        // filter out "Follow" first because instagram is gay
        for (String username : usernames) {
            if (!username.equals("Follow")) {
                newUsernames.add(username);
            }
        }

        List<String> result = new ArrayList<>();
        boolean keep = true;
        for (String username : newUsernames) {
            if (keep) {
                result.add(username);
                keep = false;
            } else if (username.equals("Remove") || username.equals("Follow")) {
                keep = true;
            } else {
                keep = false;
            }
        }
        return result;
    }

    // filters Followers list for others
    private static List<String> filterFollowers(List<String> usernames) {
        List<String> result = new ArrayList<>();
        boolean keep = true;
        for (String username : usernames) {
            if (keep) {
                result.add(username);
                keep = false;
            } else if (username.equals("Following") || username.equals("Follow") || username.equals("Requested")) {
                keep = true;
            } else {
                keep = false;
            }
        }
        return result;
    }

    // filters following list 
    private static List<String> filterFollowing(List<String> usernames) {
        List<String> result = new ArrayList<>();
        boolean keep = true;
        for (String username : usernames) {
            if (keep) {
                result.add(username);
                keep = false;
            } else if (username.equals("Following") || username.equals("Follow")) {
                keep = true;
            } else {
                keep = false;
            }
        }
        return result;
    }

    // CSV reader
    public static List<String> readCSV(String fileName){
        List<String> data = new ArrayList<>();
        try (BufferedReader file = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = file.readLine()) != null){
                data.add(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    // Prints list of OPPS
    private static void revealOpps(List<String> list) {
        for (String element : list) {
            System.out.println(element);
        }
    }
}
