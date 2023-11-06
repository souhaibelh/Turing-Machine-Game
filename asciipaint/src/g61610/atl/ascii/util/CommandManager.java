package g61610.atl.ascii.util;

import g61610.atl.ascii.model.Command;

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
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.unexecute();
            redoStack.push(command);
        }
    }

    /**
     * Redoes the previous command
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }
}
