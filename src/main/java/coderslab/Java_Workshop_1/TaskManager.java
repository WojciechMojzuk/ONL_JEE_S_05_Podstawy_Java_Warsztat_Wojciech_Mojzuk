package pl.coderslab.Java_Workshop_1;

import pl.coderslab.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        String fileN = "tasks.csv";
        printOptions();
        optionChoice(fileN);
    }

    public static void printOptions() {
        System.out.println(ConsoleColors.BLUE + "Please select an option");
        System.out.println(ConsoleColors.RESET + "add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
    }

    public static void optionChoice(String fileName) {
        Scanner scanOption = new Scanner(System.in);
        String choice = scanOption.nextLine();
        switch (choice) {
            case "list":
                listTasks(fileName);
                break;
            case "add":
                addTask(fileName);
                break;
            case "remove":
                removeTask(fileName);
                break;
            case "exit":
                System.out.println("exit");
                System.out.println(ConsoleColors.RED + "Bye, bye.");
                break;
            default:
                System.out.println("Please select a correct option.");
        }
    }

    public static String[][] getTable(String fileName) {
        File file = new File(fileName);
        String[][] table = new String[10][3];
        String row;
        int i = 0;
        String[][] table2 = new String[0][];
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                row = scan.nextLine();

                String[] parts = row.split(", ", 3);
                table[i][0] = parts[0];
                table[i][1] = parts[1];
                table[i][2] = parts[2];
                i++;
            }
            table2 = new String[i][3];
            for (int j = 0; j < i; j++) {
                for (int k = 0; k <= 2; k++) {
                    table2[j][k] = table[j][k];
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku.");
        }
        return table2;
    }

    public static void listTasks(String fileName) {
        String[][] tab = getTable(fileName);
        System.out.println("list");
        int n;
        for (n = 0; n < tab.length; n++) {
            System.out.println(n + " : " + Arrays.toString(tab[n]));
        }
    }


    public static void addTask(String fileName) {
        System.out.println("add");
        String[][] tab = getTable(fileName);
        String[][] tab2 = new String[tab.length + 1][3];
        for (int j = 0; j < tab.length; j++) {
            for (int k = 0; k <= 2; k++) {
                tab2[j][k] = tab[j][k];
            }
        }
        Scanner scanOption = new Scanner(System.in);
        System.out.println("Please add task description.");
        tab2[tab2.length - 1][0] = scanOption.nextLine();
        System.out.println("Please add task due date.");
        tab2[tab2.length - 1][1] = scanOption.nextLine();
        System.out.println("Is your task important - true/false?");
        tab2[tab2.length - 1][2] = scanOption.nextLine();
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            for (int i = 0; i < tab2.length; i++) {
                fileWriter.append(tab2[i][0] + ", " + tab2[i][1] + ", " + tab2[i][2] + "\n");
            }
        } catch (IOException ex) {
            System.out.println("Błąd zapisu do pliku.");
        }
    }

    public static void removeTask(String fileName) {
        System.out.println("remove");
        listTasks(fileName);
        System.out.println("Please select number to remove.");
        Scanner scanOption = new Scanner(System.in);
        int choice = Integer.parseInt(scanOption.nextLine());
        String[][] tab = getTable(fileName);
        String[][] tab2 = new String[tab.length - 1][3];
        int l = 0;
        for (int j = 0; j < tab2.length; j++) {
            if (j == choice) {
                l++;
            }
            for (int k = 0; k <= 2; k++) {
                tab2[j][k] = tab[j + l][k];
            }
        }
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            fileWriter.append("");
        } catch (IOException ex) {
            System.out.println("Błąd zapisu do pliku.");
        }
        for (int i = 0; i < tab2.length; i++) {
            try (FileWriter fileWriter = new FileWriter(fileName, true)) {
                fileWriter.append(tab2[i][0] + ", " + tab2[i][1] + ", " + tab2[i][2] + "\n");
            } catch (IOException ex) {
                System.out.println("Błąd zapisu do pliku.");
            }
        }
    }
}