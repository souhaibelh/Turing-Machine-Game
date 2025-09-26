package Model;

/** Defines what a consumable is, basically something u can consume, unconsume and do certain things or other things
 * depending on its consumed state
 */
public abstract class Consumable {
    private boolean consumed;

    public Consumable() {
        this.consumed = false;
    }

    void consume() {
        this.consumed = true;
    }
    void unconsume() {
        this.consumed = false;
    }
    boolean isConsumed() {
        return consumed;
    }
}
