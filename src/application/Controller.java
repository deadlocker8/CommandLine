package application;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import commands.PossibleCommands;
import commands.Command;
import commands.HistoryEntry;
import commands.HistoryType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Controller
{
	@FXML private TextArea textareaHistory;
	@FXML private TextField textfieldInput;
	
	public Stage stage;		
	private final ResourceBundle bundle = ResourceBundle.getBundle("application/", Locale.GERMANY);
	
	private ArrayList<HistoryEntry> globalHistory = new ArrayList<>();
	private int lastShwonCommand = 1;
	private ArrayList<HistoryEntry> history = new ArrayList<>();
	private final String promptText = ">>>";

	public void setStage(Stage stage)
	{
		this.stage = stage;
	}	
	
	public void init()
	{	
		textareaHistory.setEditable(false);
		textareaHistory.setWrapText(true);		
		
		textfieldInput.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{
				if(event.getCode().equals(KeyCode.ENTER))
	            {
					parse();
	            }
				
				if(event.getCode().equals(KeyCode.UP))
	            {
					showLastCommand();
	            }
				
				if(event.getCode().equals(KeyCode.ESCAPE))
	            {
					clearConsole();
	            }
			}
		});	  
			
		printPrompt();		
	}	

	public void printPrompt()
	{		
		setConsoleText();
		clearConsole();	
	}
	
	public void print(String message)
	{		
		history.add(new HistoryEntry(HistoryType.MESSAGE, message));
		setConsoleText();
		printPrompt();
	}
	
	public void clearHistoryLog()
	{
		textareaHistory.setText("");
	}
	
	public void clearHistory()
	{
		history = new ArrayList<>();
	}
	
	public void clearConsole()
	{
		textfieldInput.setText("");
		textfieldInput.requestFocus();
	}
	
	private void setConsoleText()
	{	
		clearHistoryLog();	
		
		StringBuilder sb = new StringBuilder();
		boolean printedLastEntry = false;
		for(int i = 0; i < history.size(); i++)		
		{							
			HistoryEntry currentEntry = history.get(i);
			if(currentEntry.getType().equals(HistoryType.COMMAND))
			{				
				if(printedLastEntry)
				{
					sb.append("\n");
				}
				sb.append(promptText);
				sb.append(" ");
				sb.append(currentEntry.getText());
				printedLastEntry = true;
			}
			else
			{							
				if(i != 0)
				{
					sb.append("\n");
				}
				sb.append(currentEntry.getText());
				printedLastEntry = true;											
			}
		}	
		
		textareaHistory.setText(sb.toString());	
		textareaHistory.positionCaret(sb.toString().length());
	}
	
	private boolean executeCommand(String[] command)
	{
		for(Command cmd : PossibleCommands.possibleCommands)
		{
			if(cmd.getKeyword().equals(command[0]))
			{
				cmd.execute(command, this);		
				return true;
			}
		}
		return false;
	}
	
	private void parse()
	{
		String input = textfieldInput.getText().replace("\n", "");			
		
		if(input.equals(""))
		{
			printPrompt();
			return;
		}	
		
		globalHistory.add(new HistoryEntry(HistoryType.COMMAND, input));
		history.add(new HistoryEntry(HistoryType.COMMAND, input));
		lastShwonCommand = -1;
		
		String[] command = input.split(" ");		
		if(!executeCommand(command))		
		{
			print(bundle.getString("error.unknown.command"));			
		}
		else
		{
			printPrompt();
		}
	}
	
	private void showLastCommand()
	{
		if(globalHistory.size() > 0)
		{
			if(lastShwonCommand <= 0)
			{
				textfieldInput.setText(globalHistory.get(globalHistory.size()-1).getText());
				lastShwonCommand = globalHistory.size()-1;
			}
			else
			{			
				textfieldInput.setText(globalHistory.get(lastShwonCommand - 1).getText());
				lastShwonCommand--;				
			}
		}
	}
}