package net.slintes.raspiAmpel;

/**
 * Created with IntelliJ IDEA.
 * User: slintes
 * Date: 30.12.12
 * Time: 13:24
 *
 * interface for an Ampel (traffic light)
 * use AmpelFactory to instantiate
 *
 */
public interface Ampel {

    static final int STATE_OFF = 0;
    static final int STATE_RED = 1;
    static final int STATE_YELLOW = 2;
    static final int STATE_GREEN = 4;

    /**
     * enum containing all possible states
     */
    public enum State {

        OFF(STATE_OFF),
        RED(STATE_RED),
        RED_YELLOW(STATE_RED + STATE_YELLOW),
        RED_GREEN(STATE_RED + STATE_GREEN),
        RED_YELLOW_GREEN(STATE_RED + STATE_YELLOW + STATE_GREEN),
        YELLOW(STATE_YELLOW),
        YELLOW_GREEN(STATE_YELLOW + STATE_GREEN),
        GREEN(STATE_GREEN);

        int value;
        State(int value){
            this.value = value;
        }

        @Override
        public String toString() {
            String red = (value & STATE_RED) == STATE_RED ? "RED" : "red";
            String yellow = (value & STATE_YELLOW) == STATE_YELLOW ? "YELLOW" : "yellow";
            String green = (value & STATE_GREEN) == STATE_GREEN ? "GREEN" : "green";
            return red + " - " + yellow + " - " + green;
        }
    }

    /**
     * set the new state
     * will be active until a new state is set
     *
     * @param state the new state
     */
    void setState(State state);
}
