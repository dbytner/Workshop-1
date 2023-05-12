package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Taskmanager {
    static String[][] taskToDo;

    public static void main(String[] args) {
        boolean end = true;
        Scanner scanner = new Scanner(System.in);
        taskToDo = loadList("tasks.csv");
        menu();
        while (end) {
            System.out.println("Please choose:");
            String input = scanner.next();
            switch (input) {
                case "add" -> {add();
                    System.out.println("Record has been added");}
                case "remove" -> {
                    remove(taskToDo, getIndex());
                    System.out.println("Record has been deleted");}
                case "list" -> list(taskToDo);
                case "exit" -> {
                    saveArray("tasks.csv", taskToDo);
                    System.out.println(ConsoleColors.RED + "BYE BYE");
                    end = false;
                }
                default -> System.out.println("Incorrect. Try again.");
            }
            menu();
        }
    }

    private static void menu() {

        String[] menu = {"Please select an option:", "add", "remove", "list", "exit"};

        for (int i = 0; i < menu.length; i++) {
            if (i == 0) {
                System.out.println(ConsoleColors.BLUE + menu[i]);
            } else {
                System.out.println(ConsoleColors.WHITE + menu[i]);
            }
        }

    }

    public static String[][] loadList(String fileName) {
        Path path = Paths.get(fileName);
        String[][] array = null;
        if (Files.exists(path)) {

            try {
                List<String> stringList = Files.readAllLines(path);
                array = new String[stringList.size()][stringList.get(0).split(",").length];
                for (int i = 0; i < stringList.size(); i++) {
                    String[] split = stringList.get(i).split(",");
                    for (int j = 0; j < split.length; j++) {
                        array[i][j] = split[j];
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Plik nie istnieje");
            System.exit(0);
        }
        return array;
    }

    public static void list(String[][] toDo) {
        for (int row = 0; row < toDo.length; row++) {
            System.out.print(row + ": ");
            for (int column = 0; column < toDo[row].length; column++) {
                System.out.print(toDo[row][column]);
            }
            System.out.println();
        }
    }

    public static void add() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description:");
        String taskDescription = scanner.nextLine();
        System.out.println("Please add due date");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important: true / false");
        String important = scanner.nextLine();
        taskToDo = Arrays.copyOf(taskToDo, taskToDo.length + 1);
        taskToDo[taskToDo.length - 1] = new String[3];
        taskToDo[taskToDo.length - 1][0] = taskDescription;
        taskToDo[taskToDo.length - 1][1] = dueDate;
        taskToDo[taskToDo.length - 1][2] = important;

    }

    public static void remove(String[][] array, int index) {
        try {
            if (index < array.length) {
                taskToDo = ArrayUtils.remove(array, index);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist.");
        }

    }

    public static int getIndex() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select task to remove: ");
        String text = scanner.next();
        while (!isNumberGreterEqualZero(text)) {
            System.out.println("Incorrect. Please give number greater or equal 0.");
            scanner.nextLine();
        }
        return Integer.parseInt(text);
    }

    public static boolean isNumberGreterEqualZero(String text) {
        if (NumberUtils.isParsable(text)) {
            return Integer.parseInt(text) >= 0;
        }
        return false;
    }

    public static void saveArray(String fileName, String[][] array){
        Path path = Paths.get(fileName);
        String[] lines = new String[array.length];

        for (int i = 0; i < array.length; i++){
            lines[i] = String.join(",", array[i]);
        }

        try {
            Files.write(path, Arrays.asList(lines));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}