
import com.sun.deploy.security.SelectableSecurityManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * NAME   : G.Anojan
 * IIT ID : 2019474
 * WESTMINSTER ID : W1761872
 * PURPOSE : Passengers can booking there Train seats using my programme---
 **/

public class Main extends Application { //to run the javafx method to calling the inbuilt javafx function
    // Only 42 seats in this train
    static final int seatsCount = 42;

    public static void main(String[] args) {

        launch();
    }

    
    public void start (Stage primaryStage) throws Exception{
        //create a map to store passengers details and seat number
        HashMap<String, Integer> customerDetails = new HashMap<String, Integer>();
        Scanner sc = new Scanner(System.in);
        // user can select option
        menu:
        while(true){
            System.out.println("---Denuwara Menike to Badulla Train---- ");
            System.out.println("A/C Compartment seat booking menu");
            System.out.println("-- Book your seat here --");
            System.out.println("Enter A to Add New Customer ");
            System.out.println("Enter V to View All Seats ");
            System.out.println("Enter E to View Empty Seats ");
            System.out.println("Enter D to Delete Booked Seat ");
            System.out.println("Enter F to Find Booked Seat ");
            System.out.println("Enter S to Store program data in to file ");
            System.out.println("Enter L to Load program data from file ");
            System.out.println("Enter O to View seats Ordered alphabetically by name ");
            System.out.println("Enter Q to Exit ");

            System.out.print("Enter your option : ");
            String option = sc.next();

            switch (option){
                case "A":
                case "a":
                    addCustomer(customerDetails);
                    break;
                case "V":
                case "v":
                    viewSeats(customerDetails);
                    break;
                case "E":
                case "e":
                    viewEmptySeats(customerDetails);
                    break;
                case "D":
                case "d":
                    deleteBooked(customerDetails);
                    break;
                case "F":
                case "f":
                    findSeats(customerDetails);
                    break ;
                case "S":
                case "s":
                    StoreDataToFile(customerDetails);
                    break ;
                case "L":
                case "l":
                    customerDetails = getDataFromFile();
                    break ;
                case "O":
                case "o":
                    order(customerDetails);
                    break ;
                case "Q":
                case "q":
                    // Exit the programme
                    exitProgram();
                    break menu;
            }
        }
    }
    // seat booking method
    private void addCustomer(HashMap<String, Integer> customerDetails) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("New Customer");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setOrientation(Orientation.VERTICAL);

        for(int i = 1; i <= seatsCount; i++){
            Button button = new Button("Seat " + i);
            button.setId(Integer.toString(i));
            flowPane.getChildren().add(button);

            if(customerDetails.containsValue(Integer.parseInt(button.getId()))) {
                button.setDisable(false);
                button.setStyle("-fx-background-color: blue; -fx-text-fill: white");
            } else {
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        button.setStyle("-fx-background-color: red; -fx-text-fill: black");

                        //confirm pop up box
                        Stage dialogStage = new Stage();
                        dialogStage.initModality(Modality.WINDOW_MODAL);

                        Button enter = new Button("Book");
                        TextField name = new TextField();
                        VBox vbox = new VBox(new Text("Name"),name,enter);
                        vbox.setSpacing(20);
                        vbox.setAlignment(Pos.CENTER);
                        vbox.setPadding(new Insets(15));

                        enter.setOnAction(new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent e) {
                                customerDetails.put(name.getText(),Integer.parseInt(button.getId()) );
                                System.out.println(name.getText() + " you have successfully booked seat " + button.getId());
                                dialogStage.close();
                                primaryStage.close();
                            }
                        });

                        dialogStage.setScene(new Scene(vbox,200,200));
                        dialogStage.showAndWait();
                    }
                });
            }
        }

        Scene scene = new Scene(flowPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }
    // method for view empty seats
    private void viewEmptySeats(HashMap<String, Integer> customerDetails) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Empty Seats");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setOrientation(Orientation.VERTICAL);

        for (int i = 1; i <= seatsCount; i++) {
            Button button = new Button("Seat " + i);
            button.setId(Integer.toString(i));
            flowPane.getChildren().add(button);

            if (customerDetails.containsValue(Integer.parseInt(button.getId()))) {
                button.setVisible(false);
            }
            Button enter = new Button("Cancel");
            TextField name = new TextField();
            VBox vbox = new VBox(new Text("Name"),name,enter);
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.CENTER);


        }

        Scene scene = new Scene(flowPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }
    // method for finding seats
    private void findSeats(HashMap<String, Integer> customerDetails){
        System.out.println("Enter seat booked name :");
        Scanner sc = new Scanner(System.in);
        String name  = sc.next();
        for (String passenger :customerDetails.keySet()) {
            if(name.equals(passenger)){
                System.out.println("\nPassenger Name : "+passenger+"\nSeat Number : "+customerDetails.get(passenger));
            }
        }
        

    }
    // method for saving data in file
    public static void StoreDataToFile(HashMap<String, Integer> map){
        try {
            File myObj = new File("E:\\TrainTicketBo0king\\FileDirectory\\customerDetails.txt");
            if (myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(myObj);
                for (String s : map.keySet()) {
                    myWriter.write(s+"-"+map.get(s)+"\n");
                }

                myWriter.close();

            } else {
                System.out.println("File already exists.");
                FileWriter myWriter = new FileWriter(myObj);
                for (String s : map.keySet()) {
                    myWriter.write(s+"-"+map.get(s)+"\n");
                }
                myWriter.close();
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
    // method for get the data from files
    public static HashMap<String, Integer> getDataFromFile(){
        HashMap<String, Integer> loadData = new HashMap<String, Integer>();
        try {
            File myObj = new File("E:\\TrainTicketBo0king\\FileDirectory\\customerDetails.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String [] loadfromFile = data.split("-");
                 String key = loadfromFile[0];
                int value = Integer.parseInt(loadfromFile[1]);
                loadData.put(key,value);
            }
            System.out.println("Load data from file :"+loadData);
            myReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        return loadData;
    }
    // method for Ordered alphabetically
    private void order(HashMap<String, Integer> customerDetails) {
        Map<String,Integer> result = new LinkedHashMap<>();
        customerDetails.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(
                x -> result.put(x.getKey(),x.getValue())
        );
        System.out.println("Ordered :"+result);
    }

    // seat view method
    private void viewSeats(HashMap<String, Integer> customerDetails) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Train Seats");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setOrientation(Orientation.VERTICAL);

        for (int i = 1; i <= seatsCount; i++) {
            Button button = new Button("Seat " + i);
            button.setId(Integer.toString(i));
            flowPane.getChildren().add(button);

            if (customerDetails.containsValue(Integer.parseInt(button.getId()))) {
                button.setText("Booked");
                button.setStyle("-fx-background-color: green; -fx-text-fill: blue");
            }
        }
        Scene scene = new Scene(flowPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }
    // seat deleteing method
    private void deleteBooked(HashMap<String, Integer> customerDetails){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your name : ");
        String name = scan.next();
        if(customerDetails.containsKey(name)){
            customerDetails.remove(name);
            System.out.println("Success");
        } else{
            System.out.println(" No train seats booked in this name ");
        }
    }
    public void exitProgram() {
        System.out.print("Exit from booking ----");
        System.exit(0);
    }

}
