package org.example;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static int row=0;
    static int column=0;

    static int historyOrder = 0;

    static String[][] morning;
    static String[][] afternoon;
    static String[][] evening;
    static String[] history;


    static void init() {
        morning = new String[row][column];
        afternoon = new String[row][column];
        evening = new String[row][column];
        history = new String[row * column * 3];
    }

    static void reboot(){

        System.out.print("Are you sure to reboot[y/n]:");
        String reboot= scanner.nextLine();
        if(reboot.equalsIgnoreCase("y")){
            for (int i=0;i<row;i++){
                for (int j=0;j<column;j++){

                    morning[i][j]=null;
                    afternoon[i][j]=null;
                    evening[i][j]=null;
                }
                System.out.println();
            }
            Arrays.fill(history, null);
        }
    }

    static void line() {
        System.out.println("✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨");
    }

    static void showMenu() {
        System.out.println("===>> Menu <<===");
        System.out.println("\t<A> Booking");
        System.out.println("\t<B> Show Hall");
        System.out.println("\t<C> Show Time");
        System.out.println("\t<D> Reboot");
        System.out.println("\t<E> History");
        System.out.println("\t<F> Exit");
    }

    static void showTime() {
        line();
        System.out.println("All Show Time");
        System.out.println(">> A) Morning (7:00AM-11:00AM)");
        System.out.println(">> B) Morning (12:00PM-4:00PM)");
        System.out.println(">> C) Morning (6:00PM-10:00PM)");
        line();
    }

    static void showHall() {

        System.out.println("## Hall Morning");
        for (int i=0;i<row;i++){
            for (int j=0;j<column;j++){
                System.out.print("\t");
                System.out.print("|"+(char)('A'+i)+"-"+(j+1)+"::"+(morning[i][j]==null?"AV":"BO")+"|");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("## Afternoon");
        for (int i=0;i<row;i++){
            for (int j=0;j<column;j++){
                System.out.print("\t");
                System.out.print("|"+(char)('A'+i)+"-"+(j+1)+"::"+(afternoon[i][j]==null?"AV":"BO")+"|");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("## Evening");
        for (int i=0;i<row;i++){
            for (int j=0;j<column;j++){
                System.out.print("\t");
                System.out.print("|"+(char)('A'+i)+"-"+(j+1)+"::"+(evening[i][j]==null?"AV":"BO")+"|");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void exit() {
        System.out.print("Are you sure to exit[y/n]: ");
        String exit = scanner.nextLine();
        if (exit.equalsIgnoreCase("y")) {
            System.exit(0);
        }
    }

    static void booking(){

        showTime();

        System.out.print("Select hall: ");
        String hall = scanner.nextLine();

        String[][] currentHall;
        String hallName;
        if(hall.equalsIgnoreCase("A")){
            currentHall=morning;
            hallName="Morning";
        }
        else if(hall.equalsIgnoreCase("B")){
            currentHall=afternoon;
            hallName="Afternoon";

        } else if(hall.equalsIgnoreCase("C")){
            currentHall=evening;
            hallName="Evening";
        }else {
            return;
        }

        //show current hall
        System.out.println("## "+hallName);
        for (int i=0;i<row;i++){
            for (int j=0;j<column;j++){
                System.out.print("\t");
                System.out.print("|"+(char)('A'+i)+"-"+(j+1)+"::"+(currentHall[i][j]==null?"AV":"BO")+"|");
            }
            System.out.println();
        }

        line();
        System.out.print("Enter seat to book[ex: A-1]: ");
        String seat = scanner.nextLine();

        System.out.println("Enter your name: ");
        String name = scanner.nextLine();

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String bookedDate = dateFormatter.format(dateTime);

        boolean checkBooking = false;

        loop: for (int i=0;i<row;i++){
            for (int j=0 ;j<column;j++){
                //b-1=[0,0](A-1)
                //b-1=[0,1](A-2)
                //b-1=[0,2](A-3)
                //b-1=[1,0](B-1)
                if(seat.trim().equalsIgnoreCase((char)('A'+i)+("-")+(j+1))){

                    if(currentHall[i][j]==null){

                        currentHall[i][j]="booked";
                        checkBooking=true;
                        break loop;
                    }

                }
            }
        }

        if(checkBooking){
            System.out.println("Seat "+seat+" booked successfully!!");
            String bookedHistoryTextBlock = """
                    SEAT: [%s]
                    Hall                      Name             BookedDate
                    %s                         %s               %s
                    """;

            String bookedHistory = String.format(bookedHistoryTextBlock,seat,hallName,name,bookedDate);

            history[historyOrder++]=bookedHistory;
        }else {
            System.out.println("can not booking the seat");
        }


    }

    static void showHistory(){
        line();
        for(int i=0;i<historyOrder;i++){
            System.out.println("No: "+(i+1));
            System.out.println(history[i]);
            System.out.println("------------------------------------------------------------------------------------");
        }
        line();
    }

    public static void main(String[] args) {


        line();
        System.out.println("========>> Hall Booking System <<=========");
        line();

        System.out.println("row= "+row);

        System.out.println("\n");
        System.out.print("\tEnter number of row: ");
        row = scanner.nextInt();
        System.out.print("\tEnter number of column: ");
        column = scanner.nextInt();
        System.out.println("\n");

        //init size of array
        init();


        //select option
        do {
            showMenu();
            System.out.print("Please Select Option: ");
            scanner.nextLine();
            String option = scanner.nextLine().toUpperCase();
            switch (option) {
                case "A" -> booking();
                case "B" -> showHall();
                case "C" -> showTime();
                case "D" -> reboot();
                case "E" -> System.out.println("option E");
                case "F" -> exit();
                default -> System.out.println("Please select option A to F");
            }

        } while (true);
    }
}