package Model;

import Model.enums.CommandType;

public class TuringMachineChangeEvent {
    private boolean isGameFinished;
    private int verifiedVerifierIndex;
    private boolean verifierResult;
    private int newScore;
    private boolean errorDetected;
    private int currentRound;
    private String errorMessage;
    private CommandType undoneCommandType;
    private CommandType doneCommandType;
    private CommandType errorCommandType;
    private boolean doneCommand;
    private boolean undoneCommand;
    private Code currentCode;
    private boolean isErrorCommand;
    private boolean isGameWon;

    public TuringMachineChangeEvent() {
    }

    public void setErrorCommandType(CommandType commandType) {
        this.isErrorCommand = true;
        this.undoneCommand = false;
        this.doneCommand = false;
        this.errorCommandType = commandType;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public CommandType getErrorCommandType() {
        return errorCommandType;
    }

    public boolean isErrorCommand() {
        return this.isErrorCommand;
    }

    public void setGameFinished(boolean finished) {
        this.isGameFinished = true;
    }

    public CommandType getUndoneCommandType() {
        return undoneCommandType;
    }

    public void setUndoneCommandType(CommandType undoneCommandType) {
        this.undoneCommand = true;
        this.doneCommand = false;
        this.isErrorCommand = false;
        this.undoneCommandType = undoneCommandType;
    }

    public void setCurrentRound(int round) {
        currentRound = round;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setErrorMessage(String message) {
        errorDetected = true;
        errorMessage = message;
    }

    public boolean isErrorDetected() {
        return errorDetected;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setGameWon(boolean isGameWon) {
        this.isGameWon = isGameWon;
    }

    public boolean getVerifierResult() {
        return verifierResult;
    }

    public void setVerifierResult(boolean verifierResult) {
        this.verifierResult = verifierResult;
    }

    public void setVerifierIndex(int index) {
        this.verifiedVerifierIndex = index;
    }

    public int getVerifierIndex() {
        return this.verifiedVerifierIndex;
    }

    public void setScore(int score) {
        this.newScore = score;
    }

    public int getScore() {
        return this.newScore;
    }

    public void setDoneCommandType(CommandType commandType) {
        this.doneCommand = true;
        this.undoneCommand = false;
        this.isErrorCommand = false;
        this.doneCommandType = commandType;
    }

    public CommandType getDoneCommandType() {
        return this.doneCommandType;
    }

    public boolean isDoneCommand() {
        return this.doneCommand;
    }

    public void setCode(Code code) {
        currentCode = code;
    }

    public Code getCurrentCode() {
        return currentCode;
    }

    public boolean isUndoneCommand() {
        return undoneCommand;
    }
}
