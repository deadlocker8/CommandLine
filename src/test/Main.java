package test;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import commandLine.CommandLine;
import commands.CommandBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override
	public void start(Stage stage)
	{
		try
		{
			AnchorPane mainPane = new AnchorPane();
			Scene scene = new Scene(mainPane, 600, 600);
		
			stage.setTitle("Test");
			stage.setScene(scene);
			stage.setResizable(true);						
			
			CommandLine cmd = new CommandLine(stage, null, ResourceBundle.getBundle("commandLine/", Locale.ENGLISH), new CommandBundle());
			
			HBox hbox = new HBox();
			
			Button button = new Button("Open");
			button.setOnAction(new EventHandler<ActionEvent>()
			{				
				@Override
				public void handle(ActionEvent event)
				{
					try
					{
						cmd.showCommandLine("CommandLine", 400, 250, 400, 200, -1, -1, false);
					}
					catch(IOException e)
					{
						//ERRORHANDLING
						e.printStackTrace();
					}
				}
			});
			hbox.getChildren().add(button);
			
			Button button2 = new Button("Close");
			button2.setOnAction(new EventHandler<ActionEvent>()
			{				
				@Override
				public void handle(ActionEvent event)
				{
					cmd.closeCommandLine();					
				}
			});
			hbox.getChildren().add(button2);		
			mainPane.getChildren().add(hbox);
						
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