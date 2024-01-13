import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
            List<String> followers = readCSV("GH Followers - Sheet1.csv");
            List<String> following = readCSV("EC Following - Sheet1.csv");
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
        System.out.println(followers.size());
        System.out.println(opps);
    }

    public static List<String> readCSV(String fileName){
        List<String> data = new ArrayList<>();
        boolean add = true;
        try (BufferedReader file = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = file.readLine()) != null){
                if(add == true){
                    data.add(line);
                    add = false;
                } else {
                    if(line.equals("Following")){
                        add = true;
                    } else if(line.equals("Follow")){
                        add = true;
                    } else {
                        add = false;
                    }    
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }
}
