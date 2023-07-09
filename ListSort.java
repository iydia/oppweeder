
import java.io.*;
import java.util.*;

public class ListSort{
    /**
     * Import CSV files
     * Turn into list
     * Sort list
     */

    ArrayList<Data> data;

    public ArrayList<Data> getData(){
        try {
            BufferedReader file = new BufferedReader(new FileReader("Followers.csv"));
            ArrayList<Data> data = new ArrayList<Data>();

            // Adding elements to an array
            for(int j = 0; j < 100; j++){
                Data records = new Data(file.readLine());
                data.add(records);
            }
            
            this.data = data;
            file.close();
        } catch(IOException e){
            e.printStackTrace();
        }

        return data;
    }

}