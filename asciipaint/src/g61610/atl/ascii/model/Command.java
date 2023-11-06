package g61610.atl.ascii.model;

public interface Command {
    /**
     * This method executes a command
     */
    void execute();

    /**
     * This method unexecutes a command
     */
    void unexecute();
}
