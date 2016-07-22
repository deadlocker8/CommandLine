package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override
	public void start(Stage stage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
			Parent root = (Parent)loader.load();

			Scene scene = new Scene(root, 600, 600);

			stage.setResizable(true);
			stage.setTitle("CommandLine");
			stage.setScene(scene);
			stage.setResizable(true);
			stage.setMinHeight(250);
			stage.setMinWidth(400);
			
			Controller controller = (Controller)loader.getController();
			controller.setStage(stage);	
			controller.init();		
			stage.show();		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}