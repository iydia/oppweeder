package oppweeder.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service
public class OppweederService {

    public static String runOppweederScript() {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder("node", "scripts/oppweeder.js");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error running script: " + e.getMessage();
        }
        return output.toString();
    }
}
