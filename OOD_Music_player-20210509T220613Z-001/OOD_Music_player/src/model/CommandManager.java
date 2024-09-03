package model;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class CommandManager {
	
	private static CommandManager instance = null;
    private Stack<Command> commandStackNormal;
    private Stack<Command> commandStackReverse;


    public static CommandManager getInstance(){
        if(instance != null)
            return instance;
        return new CommandManager();
    }

    private CommandManager() {
    	commandStackNormal = new Stack<>();
    	commandStackReverse = new Stack<>();
        
    }

    public void execute(Command command){
    	command.execute();
        commandStackNormal.push(command);
    }
/*
    public void undo() {
        Optional Command optionalActions = commandStackNormal.pop();
        optionalActions.ifPresent(aList -> {
            aList.forEach(Command::undo);
            commandStackReverse.push(aList);
        });
    }
*/
    public void undo() {
    	Command c = commandStackNormal.pop();
    	c.undo();
    	commandStackReverse.push(c);
    }

    public void redo() {
        Command c = commandStackReverse.pop();
        c.execute();
        commandStackNormal.push(c);
    }

    public void clearNormal() {
    	commandStackNormal.clear();
    }

    public void clearReverse() {
    	commandStackReverse.clear();
    }

}
