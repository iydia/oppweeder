import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This program cleans the copy(opps) after the JS scrape

public class JSListWeeder {
    
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
