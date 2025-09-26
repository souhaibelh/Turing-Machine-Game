package Model;

import ModelUtils.Verifier;
import java.util.Objects;

public abstract class TuringMachineVerifier extends Consumable implements Verifier {
    private final String description;
    private final int id;

    protected TuringMachineVerifier(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.getId() + ": " + this.getDescription() + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TuringMachineVerifier other = (TuringMachineVerifier) obj;
        return id == other.id && description.equals(other.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
