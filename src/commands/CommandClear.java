package commands;

import application.Controller;

public class CommandClear extends Command
{
	public CommandClear()
	{		
		super.keyword = "clear";		
		super.numberOfParams = 0;
		super.helptText = "help.clear";
	}

	@Override
	public void execute(String[] command, Controller controller)
	{		
		if(!isValid(command))
		{			
			controller.print(bundle.getString("error.invalid.arguments"));
			return;
		}	
		
		controller.clearHistory();
		controller.clearHistoryLog();
		controller.clearConsole();		
		controller.printPrompt();
	}
}