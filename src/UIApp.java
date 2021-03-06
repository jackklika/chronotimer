import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UIApp extends Application {
	 private Stage primaryStage;
	 private BorderPane rootLayout;
	 
	@Override
	public void start(Stage primaryStage) {
		 this.primaryStage = primaryStage;
	     this.primaryStage.setTitle("ChronoTimer");
	     
	     //Stuff John Added
	     Button btn = new Button();
	        btn.setText("Say 'Hello World'");
	        btn.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
	            
				public void handle(javafx.event.ActionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	        });
	        
	        StackPane root = new StackPane();
	        root.getChildren().add(btn);
	        
	        //end stuff John Added
	        
	     initRootLayout();
	     showUI();
	}
	/**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UIApp.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            primaryStage.show();
        } catch (IOException e) {
        	System.out.println("Problem in initRootLayout");
            e.printStackTrace();
        }
    }

	    /*
	     * Shows the person overview inside the root layout.
	     */
	    public void showUI() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            
	            loader.setLocation(UIApp.class.getResource("UserInterface.fxml"));
	            AnchorPane UI = (AnchorPane) loader.load();
	            
	            //Main.sim.timer.toCommand("LIST");
	            
	            rootLayout.setCenter(UI);
	        } catch (IOException e) {
	        	System.out.println("Problem in showUI");
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Returns the main stage.
	     * @return
	     */
	    public Stage getPrimaryStage() {
	        return primaryStage;
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	}