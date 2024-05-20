import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This program cleans the copy(opps) after the JS scrape

class JSListWeeder {
    public static void main(String[] args) {
        List<String> opps = readCSV("JSList - Sheet1.csv");

        revealOpps(usernames(opps));
    }

    // Isolates for usernames
    private static List<String> usernames(List<String> usernames) {
        List<String> result = new ArrayList<>();

        for (String username : usernames) {
            if (username.contains("username")) {
                // Extract the username from the line
                String[] parts = username.split(":"); // Split at ":"
                if (parts.length > 1) {
                    String extractedUsername = parts[1].trim(); // Get the part after ":"
                    // Remove extra quotes if present
                    extractedUsername = extractedUsername.replace("\"", "").trim();
                    // Remove final comma
                    extractedUsername = extractedUsername.replace(",", "").trim();
                    result.add(extractedUsername);
                }
            }
        }
        return result;
    }

    // Prints list of OPPS
    private static void revealOpps(List<String> list) {
        for (String element : list) {
            System.out.println(element);
        }
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
}