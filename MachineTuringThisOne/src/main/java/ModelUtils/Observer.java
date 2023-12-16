package ModelUtils;

import Model.TuringMachineChangeEvent;

/**
 * An observer observes and observable and updates himself based on what he observes
 */
public interface Observer {
    void update(TuringMachineChangeEvent eventChange);
}
