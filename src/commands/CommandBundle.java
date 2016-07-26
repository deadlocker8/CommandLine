package commands;

import java.util.ResourceBundle;

import commandLine.CommandLineController;

/**
 * holds important objects that are needed by the commands
 * --> fill in your variables (with getters and setters) here
 * @author deadlocker8
 *
 */
public class CommandBundle
{
	private CommandLineController controller;
	private ResourceBundle languageBundle;	

	public CommandBundle()
	{
		
	}

	public CommandLineController getController()
	{
		return controller;
	}	
	
	public ResourceBundle getLanguageBundle()
	{
		return languageBundle;
	}	

	public void setController(CommandLineController controller)
	{
		this.controller = controller;
	}

	public void setLanguageBundle(ResourceBundle languageBundle)
	{
		this.languageBundle = languageBundle;
	}	
}