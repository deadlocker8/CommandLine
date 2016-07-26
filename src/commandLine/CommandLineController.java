package commandLine;

import java.util.ArrayList;

import commands.Command;
import commands.HistoryEntry;
import commands.HistoryType;
import commands.PossibleCommands;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller for the CommandLine stage
 * @author deadlocker8
 *
 */
public class CommandLineController
{
	@FXML private TextArea textareaHistory;
	@FXML private TextField textfieldInput;
	
	private CommandLine commandLine; 
	
	public void init(CommandLine commandLine)
	{	
		this.commandLine = commandLine;		
		
		commandLine.getBundle().setController(this);	
		commandLine.getBundle().setLanguageBundle(commandLine.getLanguageBundle());	
		
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
		commandLine.history.add(new HistoryEntry(HistoryType.MESSAGE, message));
		setConsoleText();
		printPrompt();
	}
	
	public void clearHistoryLog()
	{
		textareaHistory.setText("");
	}
	
	public void clearHistory()
	{
		commandLine.history = new ArrayList<>();
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
		for(int i = 0; i < commandLine.history.size(); i++)		
		{							
			HistoryEntry currentEntry = commandLine.history.get(i);
			if(currentEntry.getType().equals(HistoryType.COMMAND))
			{				
				if(printedLastEntry)
				{
					sb.append("\n");
				}
				sb.append(commandLine.getPromptText());
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
				cmd.execute(command, commandLine.getBundle());		
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
		
		commandLine.globalHistory.add(new HistoryEntry(HistoryType.COMMAND, input));
		commandLine.history.add(new HistoryEntry(HistoryType.COMMAND, input));
		commandLine.lastShownCommand = -1;
		
		String[] command = input.split(" ");		
		if(!executeCommand(command))		
		{
			print(commandLine.getLanguageBundle().getString("error.unknown.command"));			
		}
		else
		{
			printPrompt();
		}
	}
	
	private void showLastCommand()
	{
		if(commandLine.globalHistory.size() > 0)
		{
			if(commandLine.lastShownCommand <= 0)
			{
				textfieldInput.setText(commandLine.globalHistory.get(commandLine.globalHistory.size()-1).getText());
				commandLine.lastShownCommand = commandLine.globalHistory.size()-1;
			}
			else
			{			
				textfieldInput.setText(commandLine.globalHistory.get(commandLine.lastShownCommand - 1).getText());
				commandLine.lastShownCommand--;				
			}
		}
	}
}