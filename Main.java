import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
        //System.out.println(following);
        
        revealOpps(opps);
    }

    // Filters out usernames that do not include a period, underscore, number, or alphabet
    private static List<String> filterValidUsernames(List<String> usernames) {
        List<String> filteredUsernames = new ArrayList<>();
        Pattern pattern = Pattern.compile(".*[._\\dA-Za-z].*");
        for (String username : usernames) {
            if (pattern.matcher(username).matches()) {
                filteredUsernames.add(username);
            }
        }
        return filteredUsernames;
    }

    // Filters out names and keeps usernames
    // Assumes list is prefiltered into repeating [username, name] entries
    private static List<String> usernames(List<String> list) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += 2) {
            result.add(list.get(i));
        }
        return result;
    }

    // Filters out the "Remove" and "Follow" keywords from Followers
    private static List<String> filterFollowers(List<String> usernames) {
        List<String> newUsernames = new ArrayList<>();

        // filter out "Follow" first because CSV is weirfd
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

    // Filters out the "Following" keyword from Following
    private static List<String> filterFollowing(List<String> usernames) {
        List<String> result = new ArrayList<>();
        boolean keep = true;
        for (String username : usernames) {
            if (keep) {
                result.add(username);
                keep = false;
            } else if (username.equals("Following")) {
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
