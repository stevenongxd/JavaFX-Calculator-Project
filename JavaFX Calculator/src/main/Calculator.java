package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Calculator extends VBox implements EventHandler<ActionEvent> {
    
    private TextField inputTF;
    private double previousValue;
    private String operator;
    private boolean operatorClicked;
    private boolean dotClicked;
    
    public Calculator() {
        inputTF = new TextField();
        inputTF.setAlignment(Pos.CENTER_RIGHT);
        inputTF.setEditable(false);
        inputTF.setPrefHeight(50);
        
        GridPane buttonGrid = new GridPane();
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.setPadding(new Insets(10));
        
        addButton(buttonGrid, "(", 0, 0);
        addButton(buttonGrid, ")", 1, 0);
        addButton(buttonGrid, "AC", 2, 0);
        addButton(buttonGrid, "C", 3, 0);
        
        addButton(buttonGrid, "7", 0, 1);
        addButton(buttonGrid, "8", 1, 1);
        addButton(buttonGrid, "9", 2, 1);
        addButton(buttonGrid, "/", 3, 1);
        
        addButton(buttonGrid, "4", 0, 2);
        addButton(buttonGrid, "5", 1, 2);
        addButton(buttonGrid, "6", 2, 2);
        addButton(buttonGrid, "*", 3, 2);
        
        addButton(buttonGrid, "1", 0, 3);
        addButton(buttonGrid, "2", 1, 3);
        addButton(buttonGrid, "3", 2, 3);
        addButton(buttonGrid, "-", 3, 3);
        
        addButton(buttonGrid, "0", 0, 4);
        addButton(buttonGrid, ".", 1, 4);
        addButton(buttonGrid, "+/-", 2, 4);
        addButton(buttonGrid, "+", 3, 4);
        
        addButton(buttonGrid, "sin", 4, 1);
        addButton(buttonGrid, "cos", 4, 2);
        addButton(buttonGrid, "tan", 4, 3);
        addButton(buttonGrid, "log", 4, 4);
        
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10));
        
        Button equalsButton = new Button("=");
        equalsButton.setPrefWidth(70);
        equalsButton.setOnAction(this);
        
        bottomBox.getChildren().addAll(new Label("Result: "), inputTF, equalsButton);
        
        this.getChildren().addAll(inputTF, buttonGrid, bottomBox);
        operatorClicked = false;
        dotClicked = false;
    }
    private void addButton(GridPane grid, String label, int x, int y) {
        Button button = new Button(label);
        button.setPrefWidth(50);
        button.setPrefHeight(50);
        button.setOnAction(this);
        grid.add(button, x, y);
    }

    private void handleNumberClick(String number) {
        if (operatorClicked) {
            inputTF.setText(number);
            operatorClicked = false;
            dotClicked = false;
        } else {
            inputTF.setText(inputTF.getText() + number);
        }
    }

    private void handleOperatorClick(String operator) {
        if (operator.equals("+/-")) {
            double currentValue = Double.parseDouble(inputTF.getText());
            inputTF.setText(String.valueOf(-currentValue));
        } else {
            if (dotClicked) {
                inputTF.setText(inputTF.getText() + "0");
            }
            previousValue = Double.parseDouble(inputTF.getText());
            this.operator = operator;
            operatorClicked = true;
            dotClicked = false;
        }
    }

    private void handleEqualsClick() {
        if (operator != null) {
            double currentValue = Double.parseDouble(inputTF.getText());
            double result = 0;
            switch (operator) {
                case "+":
                    result = previousValue + currentValue;
                    break;
                case "-":
                    result = previousValue - currentValue;
                    break;
                case "*":
                    result = previousValue * currentValue;
                    break;
                case "/":
                    result = previousValue / currentValue;
                    break;
                case "sin":
                    result = Math.sin(currentValue);
                    break;
                case "cos":
                    result = Math.cos(currentValue);
                    break;
                case "tan":
                    result = Math.tan(currentValue);
                    break;
                case "log":
                    result = Math.log(currentValue);
                    break;
            }
            inputTF.setText(String.valueOf(result));
            operator = null;
            dotClicked = false;
        }
    }

    private void handleClearClick() {
        inputTF.setText("");
        operator = null;
        operatorClicked = false;
        dotClicked = false;
    }

    private void handleAllClearClick() {
        handleClearClick();
        previousValue = 0;
    }

    private void handleDotClick() {
        if (!dotClicked) {
            inputTF.setText(inputTF.getText() + ".");
            dotClicked = true;
        }
    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        String label = button.getText();
        switch (label) {
            case "AC":
                handleAllClearClick();
                break;
            case "C":
                handleClearClick();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "sin":
            case "cos":
            case "tan":
            case "log":
            case "+/-":
                handleOperatorClick(label);
                break;
            case ".":
                handleDotClick();
                break;
            case "=":
                handleEqualsClick();
                break;
            default:
                handleNumberClick(label);
                break;
        }
    }
}