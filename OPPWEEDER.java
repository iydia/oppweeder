import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OPPWEEDER {
    public static void main(String[] args) {
            List<String> followers = dataCollect("Followers.csv", "Remove");
            List<String> following = dataCollect("Following.csv", "Following");
            //List<String> dogs = new ArrayList<>();
            //dogs.addAll(followers);

        System.out.println(followers);
        System.out.println(following);
    }

    public static List<String> dataCollect(String fileName, String fillerWord){
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
