package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class TaskMenager {
    static String [][] tasks; // dlaczego jak mam tasks w mainie wszedzie gdzie chce jej uzyc mam blad ze zmiennej nie ma?

    public static void main(String[] args) {
        String fileName = "tasks.csv";
        tasks = downloadDataFromFile(fileName);
        printOptions();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String choiceOption = scanner.nextLine().toLowerCase();

            switch (choiceOption) {
                case "add":
                    addTaskToFile();
                    break;
                case "list":
                    printTasks(tasks);
                    break;
                case "remove":
                    removeTask();
                    break;
                case "exit":
                    exitFromTaskMenager(tasks, fileName);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Not select a correct options");
            }
            printOptions();
        }
    }

    public static void printOptions(){
        String[] options = {"Add", "List", "Remove", "Exit"};
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please, select an options." + ConsoleColors.RESET);
        for (int i = 0; i < options.length; i++){
            System.out.println(options[i]);
        }
        System.out.println();
    }

    public static String[][] downloadDataFromFile(String fileName){
        Path path = Paths.get(fileName);
        if(!Files.exists(path)){
            System.out.println("File not found!");
            System.exit(0);
        }
        String[][] array = null;
        try {
            List<String> lines = Files.readAllLines(path);
            array = new String[lines.size()][lines.get(0).split(", ").length];
                    for (int i = 0; i < lines.size(); i++){ //tutaj miałem duzy problem i szczerze to w 100% patrzyłem na waszego gotowca.
                        String [] tab = lines.get(i).split(","); // jakby jakos to rozjaśnić byłoby wspaniale
                        for(int j = 0; j < tab.length; j++) {
                            array[i][j] = tab[j];
                        }
                    }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("File not found");
        }
        return array;
    }

    public static void addTaskToFile(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, add task description:");
        String desctription = scanner.nextLine();

        System.out.println("Please, add task date");
        String date = scanner.nextLine();

        System.out.println("Task is important? (False / True)");
        String bool = scanner.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length+1);  // dlaczego jak chciałem dac argument metody np: String [][] array i potem
        tasks[tasks.length-1]= new String[3];                    // przy wywoalniu funkcji wpisac tablice "tasks" program nie działa??
        tasks[tasks.length-1][0] = desctription;                // podejrzewam że reszta metod bedzie tak samo działało, że bede musial podstawić oryginal tablicy
        tasks[tasks.length-1][1] = date;                        // sprawdziłem to reszta funckji działa jak pdomieniam na oryginal tablic tasks. dlaczego?
        tasks[tasks.length-1][2] = bool;
    }

    public static void printTasks(String[][] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(i + ":");
            for(int j = 0; j < array[i].length; j++){
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }

    public static void removeTask(){
        System.out.println("Please, select a number to remove task:");
        Scanner scanner = new Scanner(System.in);
        String numberToRemove = scanner.nextLine();

        if((!StringUtils.isNumeric(numberToRemove )) && (!(Integer.parseInt(numberToRemove) >= 0)) && (!(Integer.parseInt(numberToRemove) < tasks.length))){
            System.out.println("Selected number is invalid");
        } else {
            tasks = ArrayUtils.remove(tasks, Integer.parseInt(numberToRemove));
        }
    }

    public static void exitFromTaskMenager(String [][] array, String nameoffile){
        Path path = Paths.get(nameoffile);
        String[] tempArray = new String[array.length];
        for(int i = 0; i < array.length; i++){
            tempArray[i] = String.join(",", array[i]);
        }
        try{
            Files.write(path, Arrays.asList(tempArray));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("File not exist!");
        }
        System.out.println(ConsoleColors.RED);
        System.out.println("Bye, Bye"+ ConsoleColors.RESET);
    }
}