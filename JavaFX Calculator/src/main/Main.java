package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Calculator calculatorGUI = new Calculator();
        Scene scene = new Scene(calculatorGUI, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Scientific Calculator");
        primaryStage.show();
	}

}
