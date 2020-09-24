import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Todo {

    static String storageFile = "tasks.txt";
    
    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            printUsage();
        }
        if (args[0].equals("-l")) {
            listAllTasks();
        }


    }

    private static void listAllTasks() throws IOException {
        Path filePath = Paths.get(storageFile);

        try {
            List<String> lines = Files.readAllLines(filePath);
            int i = 1;
            for (String line : lines) {
                System.out.println(i + " - " + line);
                i++;
            }
        } catch (NoSuchFileException e) {
            System.out.println("Error: unable to read file");
        }
    }

    private static void printUsage() {
        System.out.println("Command Line Todo application");
        System.out.println("=============================\n");

        System.out.println("Command line arguments:");
        System.out.println("-l   Lists all the tasks");
        System.out.println("-a   Adds a new task");
        System.out.println("-r   Removes an task");
        System.out.println("-c   Completes an task");
    }
}
