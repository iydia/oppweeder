import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OPPWEEDER {
    public static void main(String[] args) {
            List<String> followers = readCSV("ALL Followers.csv", "Remove");
            List<String> following = readCSV("ALL Following.csv", "Following");
            List<String> dawgs = new ArrayList<>();
            dawgs.addAll(followers);
            dawgs.retainAll(following);
            List<String> disciples = new ArrayList<>();
            disciples.addAll(followers);
            disciples.removeAll(following);
            List<String> opps = new ArrayList<>();
            opps.addAll(following);
            opps.removeAll(followers);
        //System.out.println(dawgs);
        //System.out.println(disciples);
        System.out.println(opps);
    }

    public static List<String> readCSV(String fileName, String fillerWord){
        List<String> data = new ArrayList<>();
        boolean add = true;
        try (BufferedReader file = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = file.readLine()) != null){
                if(!line.equals(fillerWord)){
                    if (add == true){
                        data.add(line);
                        add = false;
                    }
                } else {
                    add = true;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }
}
