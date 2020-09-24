import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Todo {

    static String storageFile = "../files/tasks.txt"; // for cmdline
    //static String storageFile = "files/tasks.txt"; // for debug
    
    public static void main(String[] args) {

        if (args.length == 0) {
            printUsage();
        } else if (args[0].equals("-l")) {
            listAllTasks();
        } else if (args[0].equals("-a")) {
            if (args.length < 2) {
                System.out.println("Unable to add: no task provided");
            } else {
                addTask(args[1]);
            }
        } else if (args[0].equals("-r")) {
            if (args.length < 2) {
                System.out.println("Unable to remove: no index provided");
            } else {
                try {
                    removeTask(Integer.parseInt(args[1]));
                } catch (NumberFormatException e) {
                    System.out.println("Unable to remove: index is not a number");
                }
            }
        } else if (args[0].equals("-c")) {
            if (args.length < 2) {
                System.out.println("Unable to check: no index provided");
            } else {
                try {
                    checkTask(Integer.parseInt(args[1]));
                } catch (NumberFormatException e) {
                    System.out.println("Unable to check: index is not a number");
                }
            }
        } else {
            System.out.println("Unsupported argument");
            printUsage();
        }
    }

    private static void checkTask(int index) {
        Path filePath = Paths.get(storageFile);
        try {
            List<String> lines = Files.readAllLines(filePath);
            if (index > lines.size()) {
                System.out.println("Unable to check: index is out of bound");
                return;
            }
            List<String> newLines = new ArrayList<>();
            int i = 1;
            for (String line : lines) {
                if (i != index) {
                    newLines.add(line);
                } else {
                    String task = line.substring(4);
                    String checkedTask = "[X] " + task;
                    newLines.add(checkedTask);
                }
                i++;
            }
            Files.write(filePath, newLines);
        } catch (NoSuchFileException e) {
            System.out.println("Error: unable to read file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeTask(int numberTaskToRemove) {
        Path filePath = Paths.get(storageFile);
        try {
            List<String> lines = Files.readAllLines(filePath);
            if (numberTaskToRemove > lines.size()) {
                System.out.println("Unable to remove: index is out of bound");
                return;
            }
            List<String> newLines = new ArrayList<>();
            int i = 1;
            for (String line : lines) {
                if (i != numberTaskToRemove) {
                    newLines.add(line);
                }
                i++;
            }
            Files.write(filePath, newLines);
        } catch (NoSuchFileException e) {
            System.out.println("Error: unable to read file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addTask(String task) {
        Path filePath = Paths.get(storageFile);
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(storageFile, true));
            output.append("[ ] " + task);
            output.newLine();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void listAllTasks() {
        Path filePath = Paths.get(storageFile);

        try {
            List<String> lines = Files.readAllLines(filePath);
            if (lines.size() == 0) {
                System.out.println("No todos for today! :)");
            } else {
                int i = 1;
                for (String line : lines) {
                    System.out.println(i + " - " + line);
                    i++;
                }
            }
        } catch (NoSuchFileException e) {
            System.out.println("Error: unable to read file");
        } catch (IOException e) {
            e.printStackTrace();
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
