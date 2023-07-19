import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OPPWEEDER {
    public static void main(String[] args) {
            List<String> followers = dataCollect("Followers.csv");
            List<String> following = dataCollect("Following.csv");

        System.out.println(followers);
        System.out.println(following);
    }

    public static List<String> dataCollect(String fileName){
        List<String> data = new ArrayList<>();
        int count = 0;

        try (BufferedReader file = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = file.readLine()) != null) {
                switch(count){
                    case 0:
                        data.add(line);
                        count+=1;
                        break;
                    case 1:
                        count-=1;
                        break;
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
