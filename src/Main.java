

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    /**
     * Propogated {@link IOException} here
     * {@link #readFile} and {@link #writeOutput} methods should be called here
     * A {@link Scheduler} instance must be instantiated here
     */
    public static void main(String[] args) throws IOException {

        Assignment[] assignment = readFile(args[0]);
        Arrays.sort(assignment);  // sort assignments ascending order
        Scheduler scheduler = new Scheduler(assignment);

        ArrayList<Assignment> solDynamic = new ArrayList<>();
        solDynamic = scheduler.scheduleDynamic();

        Collections.reverse(solDynamic);
        writeOutput("solution_dynamic.json", solDynamic);
        writeOutput("solution_greedy.json", scheduler.scheduleGreedy());

    }

    /**
     * @param filename json filename to read
     * @return Returns a list of {@link Assignment}s obtained by reading the given json file
     * @throws FileNotFoundException If the given file does not exist
     */
    private static Assignment[] readFile(String filename) throws FileNotFoundException {
        File input = new File(filename);
        JsonElement el = JsonParser.parseReader(new FileReader(input));
        JsonArray js = el.getAsJsonArray();

        Gson gson = new Gson();
        return gson.fromJson(js, Assignment[].class);
    }

    /**
     * @param filename  json filename to write
     * @param arrayList a list of {@link Assignment}s to write into the file
     * @throws IOException If something goes wrong with file creation
     */
    private static void writeOutput(String filename, ArrayList<Assignment> arrayList) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        Writer fw = new FileWriter(filename);
        gson.toJson(arrayList, fw);
        fw.close();
    }
}
