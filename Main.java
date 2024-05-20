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
        List<String> dawgs = new ArrayList<>();
        dawgs.addAll(followers);
        dawgs.retainAll(following);
        List<String> disciples = new ArrayList<>();
        disciples.addAll(followers);
        disciples.removeAll(following);
        List<String> opps = new ArrayList<>();
        opps.addAll(following);
        opps.removeAll(followers);
        //System.out.println(followers);
        //System.out.println(following);
        //System.out.println(dawgs);
        //System.out.println(disciples);
        printList(filterUsernames(filterValidUsernames(opps)));
    }

    // Filters out names with spaces
    private static List<String> filterUsernames(List<String> usernames) {
        List<String> filteredUsernames = new ArrayList<>();
        for (String username : usernames) {
            if (!username.contains(" ")) {
                filteredUsernames.add(username);
            }
        }
        return filteredUsernames;
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

    // CSV scraper
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

    // Prints individual entries in newlines
    private static void printList(List<String> list) {
        for (String element : list) {
            System.out.println(element);
        }
    }

}
