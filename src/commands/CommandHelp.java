package commands;

import application.Controller;

public class CommandHelp extends Command
{
	public CommandHelp()
	{
		super();	
		super.keyword = "help";		
		super.numberOfParams = 1;
		super.helptText = "help.help";
	}

	@Override
	public void execute(String[] command, Controller controller)
	{		
		if(!isValid(command))
		{			
			controller.print(bundle.getString("error.invalid.arguments"));
			return;
		}	
		
		for(Command cmd : PossibleCommands.possibleCommands)
		{
			if(cmd.getKeyword().equals(command[1]))
			{				
				controller.print(bundle.getString("help." + command[1]));		
				return;
			}
		}		
				
		controller.print(bundle.getString("error.no.help"));		
	}
}
