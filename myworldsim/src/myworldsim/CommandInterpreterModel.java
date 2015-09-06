package myworldsim;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class CommandInterpreterModel
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private CommandTree<CommandModel> 	commandTree;
	private boolean 					executionStatus;
	private Controller					controller;
	/*
	 * Class Constants
	 * 
	 */
	
	private static final String			RESERVED_WORD 		= "_reserved";
	public static final String[]			COMMAND_NOT_FOUND_MESSAGES 	= {
																	"I don't now how to do that.",
																	"Huh?",
																	"Gibberish!",
																	"I don't know what that means.",
																	"Leave me alone you jerk.",
																	"Um...",
																	"What?",
																	"Eh?",
																	"Was that engrish?",
																	"Are you Cuban?",
																	"Learn to type",
																	"Do you need help? Tuu bad doofus!",
																	"SUREEEEEEE......",
																	"Say help for help, NOT...",
																	"PLEASE TYPE AGAIN SIRRR",
																	"你臭!",
																	"Yaddaydaa",
																	"Pie Face!",
																	"Read the GUIDE!",
																	"Check your spelling 3rd grader!"
																	
																	
															      };
	
	/*
	 * Constructor
	 * 
	 */
	public CommandInterpreterModel()
	{
		
	}
	public CommandInterpreterModel(Controller controller) {
		this.controller=controller;
		commandTree = new CommandTree<CommandModel>();
	}
	
	/*
	 * Getters and Setters
	 * 
	 */
	
	public boolean getStatus() {
		return executionStatus;
	}
	
	
	/*
	 * buildBinarySearchCommandTree Methods
	 * 
	 */
	
	public boolean buildBinarySearchCommandTree(List<CommandModel> commandList) {
		
		boolean built = false;
			
		if (commandList.size() > 0) {

			for (CommandModel commandModel : commandList) {				
				commandTree.insert(commandModel);
			}
			
			built=true;
		}

		return built;
	}
	
	/*
	 * Get Commands Methods
	 * 
	 */
	
	public List<CommandModel> getCommands() {
		return commandTree.preOrder();
	}
	
	public List<CommandModel> getCommandsInOrder() {
		return commandTree.inOrder();
	}
	
	public List<CommandModel> getPrimaryCommands() {
		
		List<CommandModel> commandModelList 	= getCommandsInOrder();
		List<CommandModel> primaryCommandList 	= new LinkedList<CommandModel>();
		
		for (CommandModel commandModel: commandModelList) {
			
			if (commandModel.getPrimaryCommandId() == 0) {
				primaryCommandList.add(commandModel);
			}
		}
		
		return primaryCommandList;
	}
	
	public List<CommandModel> getSynonymCommands() {
		
		List<CommandModel> commandModelList 	= getCommandsInOrder();
		List<CommandModel> synonymCommandList 	= new LinkedList<CommandModel>();
		
		for (CommandModel commandModel: commandModelList) {
			
			if (commandModel.getPrimaryCommandId() > 0) {
				synonymCommandList.add(commandModel);
			}
		}
		
		return synonymCommandList;
	}

	/*
	 * findCommand Methods
	 * 
	 */
	
	public CommandModel findCommand(String command) {
		
		CommandModel commandModel = new CommandModel();
		commandModel.setCommandName(command);
		
		CommandTreeNode<CommandModel> commandTreeNode = commandTree.find(commandModel);

		// Check if the command was found
		if (commandTreeNode.getData() != CommandTreeNode.NOT_FOUND) {
			
			// Check if it is a Synonym Command
			commandModel = (CommandModel)commandTreeNode.getData();
			
			if (commandModel.getPrimaryCommandId() > 0) {
				
				// Find the Primary Command. Caller is responsible for testing whether or not it was found (call getCommandId()).
				commandModel = findCommand(commandModel.getPrimaryCommandName());
			}
		}
		
		return commandModel;
	}
	
	/*
	 * Commands
	 * 
	 */
	
	public String executeCommand(CommandModel commandModel, Location commandObject) {

		String	executionMessage = "";
		boolean executed 		 = false;
		
		// Find the command and get its command ID		
		CommandTreeNode<CommandModel> commandTreeNode = commandTree.find(commandModel);

		// Check if the command was found
		if (commandTreeNode.getData() != CommandTreeNode.NOT_FOUND) {
			
			commandModel = (CommandModel) commandTreeNode.getData();
		
			// String parameterl
			Class<?>[] paramString = new Class[2];
			paramString[0] = CommandModel.class;
			paramString[1] = Location.class;
			
			try {
				Class<?> cls = Class.forName("myworldsim.CommandInterpreterModel");
				Object obj = cls.newInstance();
		 
				// Call the Command method, pass a String param 
				Method method = cls.getDeclaredMethod(commandModel.getCommandName(), paramString);
				method.invoke(obj, commandModel, commandObject);
				if(commandModel.getNewLocation() !=null)
				{
					controller.setCurrentLocation(commandModel.getNewLocation());
				}
				executionMessage = commandModel.getCommandText();
				executed = true;
			}
			catch(ClassNotFoundException e){
				System.out.println("CommandInterpreterModel::executeCommand(): Class myworldsim.CommandInterpreterModel not found.");
			}
			catch(IllegalAccessException e){
				System.out.println("CommandInterpreterModel::executeCommand(): Class myworldsim.CommandInterpreterModel not found.");
			}
			catch(InstantiationException e){
				System.out.println("CommandInterpreterModel::executeCommand(): Class myworldsim.CommandInterpreterModel not found.");
			}
			catch(NoSuchMethodException e){
				
				// Check for a reserved word Command
				try {
					Class<?> cls = Class.forName("myworldsim.CommandInterpreterModel");
					Object obj = cls.newInstance();
			 
					// Call the Command method, pass a String param 
					Method method = cls.getDeclaredMethod(commandModel.getCommandName() + RESERVED_WORD, paramString);
					method.invoke(obj, commandModel, commandObject);

					executionMessage = commandModel.getCommandText();
					executed 		 = true;
				}
				catch(ClassNotFoundException ex){
					System.out.println("CommandInterpreterModel::executeCommand(): Class myworldsim.CommandInterpreterModel not found.");
				}
				catch(IllegalAccessException ex){
					System.out.println("CommandInterpreterModel::executeCommand(): Class myworldsim.CommandInterpreterModel not found.");
				}
				catch(InstantiationException ex){
					System.out.println("CommandInterpreterModel::executeCommand(): Class myworldsim.CommandInterpreterModel not found.");
				}
				catch(NoSuchMethodException ex){
					System.out.println("CommandInterpreterModel::executeCommand(): Method " + commandModel.getCommandName() + RESERVED_WORD + " in class myworldsim.CommandInterpreterModel not found.");
					executionMessage = COMMAND_NOT_FOUND_MESSAGES[Utility.randomizer(COMMAND_NOT_FOUND_MESSAGES.length) - 1];
				}
				catch(InvocationTargetException ex){
					System.out.println("CommandInterpreterModel::executeCommand(): Method " + commandModel.getCommandName() + RESERVED_WORD + " in class myworldsim.CommandInterpreterModel not found.");
					executionMessage = COMMAND_NOT_FOUND_MESSAGES[Utility.randomizer(COMMAND_NOT_FOUND_MESSAGES.length) - 1];
					
					
				}
			}
			catch(InvocationTargetException e){
				
				// Check for a reserved word Command
				try {
					Class<?> cls = Class.forName("myworldsim.CommandInterpreterModel");
					Object obj = cls.newInstance();
			 
					// Call the Command method, pass a String param 
					Method method = cls.getDeclaredMethod(commandModel.getCommandName() + RESERVED_WORD, paramString);
					method.invoke(obj, commandModel, commandObject);

					executionMessage = commandModel.getCommandText();
					executed 		 = true;
				}
				catch(ClassNotFoundException ex){
					System.out.println("CommandInterpreterModel::executeCommand(): Class myworldsim.CommandInterpreterModel not found.");
				}
				catch(IllegalAccessException ex){
					System.out.println("CommandInterpreterModel::executeCommand(): Class myworldsim.CommandInterpreterModel not found.");
				}
				catch(InstantiationException ex){
					System.out.println("CommandInterpreterModel::executeCommand(): Class myworldsim.CommandInterpreterModel not found.");
				}
				catch(NoSuchMethodException ex){
					System.out.println("CommandInterpreterModel::executeCommand(): Method " + commandModel.getCommandName() + RESERVED_WORD + " in class myworldsim.CommandInterpreterModel not found.");
					executionMessage = COMMAND_NOT_FOUND_MESSAGES[Utility.randomizer(COMMAND_NOT_FOUND_MESSAGES.length) - 1];;
				}
				catch(InvocationTargetException ex){
					System.out.println("CommandInterpreterModel::executeCommand(): Method " + commandModel.getCommandName() + RESERVED_WORD + " in class myworldsim.CommandInterpreterModel not found.");
					executionMessage = COMMAND_NOT_FOUND_MESSAGES[Utility.randomizer(COMMAND_NOT_FOUND_MESSAGES.length) - 1];;
				}
			}
		}
		
		executionStatus = executed;
		return executionMessage;
	}
		
	private void move(CommandModel commandModel, Location currentLocation) {
		
		if (commandModel.getNumberOfParameters() > 0) {
			
			String[] commandParameters = commandModel.getCommandParameters();
			
			
			// Move to requested location
			
			int exitNumber= currentLocation.getExit(commandParameters[0]);
			
			if (exitNumber >= 0) 
			{
				Location newLocation=currentLocation.getExit(exitNumber).getExitToLocation();
				commandModel.setNewLocation(newLocation);

				commandModel.setCommandText( "Moved to " + newLocation.getDescription() + "\n");
				
							
			}else{
				commandModel.setCommandText("Stop blabbing nonsense");
			}
		}
		else {
			commandModel.setCommandText("Where would you like me to move?");
		}	
	}
	
	private void throw_reserved(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void shoot(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void get(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void open(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void close(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void sit(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void stand(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void hit(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void break_reserved(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void light(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void cut( CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void cook(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void kill(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void eat(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void launch(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void pickup(CommandModel commandModel, Location currentLocation){
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void take(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void shutoff(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void tighten(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void turnon(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void throwup(CommandModel commandModel, Location currentLocation) {
		
		//System.out.println("Let's " + command + "!");
	}
	
	private void build(CommandModel commandModel, Location currentLocation){
		
		//System.out.println("Let's build!");

	}
}
