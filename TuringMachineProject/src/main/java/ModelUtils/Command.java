package ModelUtils;

import Model.enums.CommandType;

public interface Command {
    /**
     * This method executes a command
     */
    void execute();

    /**
     * This method unexecutes a command
     */
    void unexecute();

    CommandType getCommandType();
}
