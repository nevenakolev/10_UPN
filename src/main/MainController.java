package main;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    //Variables
    @FXML TextField txtf_input = new TextField();
    @FXML ListView<Double> lstv_stack = new ListView();
    @FXML Label lbl_alert = new Label();
    Stack<Double> stack = new Stack<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML public void hdl_buttonClicked(Event event) {
        //gets the button
        Object obj = event.getSource();
        Button btn_clicked = (Button)obj;
        int value;
        String op;

        //checks if the pressed button is a number
        boolean isNumber;
        try {
            value = Integer.parseInt(btn_clicked.getText());
            txtf_input.setText(txtf_input.getText() + value);
            isNumber = true;
        } catch(Exception ex) {
            isNumber = false;
        }

        //checks if the pressed button is a operator
        if(!isNumber) {
            //it is an operator
            op = btn_clicked.getText();

            switch(op) {
                case "Enter":
                    try {
                        //checks if the input is a numeric value
                        double num = Double.parseDouble(txtf_input.getText());
                        //pushes the number into the stack
                        stack.push(num);
                        //adds the number to the listview
                        initListView();
                        //clears the entry
                        txtf_input.setText("");
                        //clears the alert
                        clearAlert();
                    } catch(Exception ex) {
                        lbl_alert.setText("Please insert a numeric value");
                    }
                    break;

                case "C": //clears the entry and the stack
                    //clears the entry
                    txtf_input.setText("");
                    //clears the stack
                    while(!stack.empty()) {stack.pop();}
                    //clears the listView
                    lstv_stack.getItems().clear();
                    break;

                case "CE": //clears entry
                    txtf_input.setText("");
                    break;

                case "+":
                case "-":
                case "*":
                case "/":
                    //Function to calculate
                    doCalculation(op);
                    break;

                case "1/x":
                    if(!stack.empty()) {
                        double xx = stack.pop();
                        stack.push(1/xx);
                        //updates the listview
                        initListView();
                        //clears the alert
                        clearAlert();
                    } else {
                        lbl_alert.setText("Please enter a value first!");
                    }

                    break;

                case "x <-> y":
                    if (stack.size() >= 2) {
                        //top value
                        double x = stack.pop();
                        //bottom value
                        double y = stack.pop();

                        //x and y change places
                        stack.push(x);
                        stack.push(y);
                        //updates the listview
                        initListView();
                        //clears the alert
                        clearAlert();
                    } else {
                        lbl_alert.setText("Please insert another value!");
                    }
                    break;

                case ".":
                    txtf_input.setText(txtf_input.getText() + ".");
                    break;

                case "(-)": //negative number
                    try {
                        double lastValue = stack.pop();
                        stack.push(lastValue * (-1));
                        initListView();
                        //clears the alert
                        clearAlert();
                    } catch(Exception ex) {
                        lbl_alert.setText("Please insert another value");
                    }
            }
        }
        //if the button was a number, the if-branch should be ignored
    }

    public void doCalculation(String operator) {
        if(!stack.empty() && stack.size() >= 2) {
            //gets the value which got inserted the last
            double lastValue = stack.pop();

            switch (operator) {
                case "+":
                    lstv_stack.getItems().remove(0);
                    stack.push(stack.pop() + lastValue);
                    clearAlert();
                    break;

                case "-":
                    lstv_stack.getItems().remove(0);
                    stack.push(stack.pop() - lastValue);
                    clearAlert();
                    break;

                case "*":
                    lstv_stack.getItems().remove(0);
                    stack.push(stack.pop() * lastValue);
                    clearAlert();
                    break;

                case "/":
                    System.out.println(lastValue);
                    if(lastValue != 0) {
                        lstv_stack.getItems().remove(0);
                        stack.push(stack.pop() / lastValue);
                        clearAlert();
                    } else {
                        lbl_alert.setText("Division by zero is not allowed!");
                    }
                    break;
            }
            initListView();
        } else {
            lbl_alert.setText("Please insert another value!");
        }
    }

    public void initListView() {
        //clears the listview
        lstv_stack.getItems().clear();
        //fills the listview with numbers from the stack
        for (int i = stack.size(); i > 0; i--) {
            lstv_stack.getItems().add(stack.get(i-1));
        }
    }

    public void clearAlert() {
        lbl_alert.setText("");
    }
}
