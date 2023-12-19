package ModelUtils;

import Model.enums.CommandType;

import java.util.Stack;

/**
 * Manages the commands and does/undoes them
 */
public class CommandManager {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    /**
     * Method that adds a new command to the stack
     * @param command command to add
     */
    public void newCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    /**
     * Undoes the previous command
     */
    public Command undo() {
        Command undoCommand = null;
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            undoCommand = command;
            command.unexecute();
            redoStack.push(command);
        }
        return undoCommand;
    }

    /**
     * Redoes the previous command
     */
    public Command redo() {
        Command redoCommand = null;
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            redoCommand = command;
            command.execute();
            undoStack.push(command);
        }
        return redoCommand;
    }
}

