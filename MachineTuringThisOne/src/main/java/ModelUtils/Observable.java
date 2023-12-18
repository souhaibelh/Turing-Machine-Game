package ModelUtils;

import Model.TuringMachineChangeEvent;

import java.util.List;

/**
 * Defines what an observable is, It's something we can observe, we can add stuff to observe it, remove stuff to observe it
 * and notify its observers for the change they are looking for.
 */
public interface Observable {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers(TuringMachineChangeEvent event);
}
