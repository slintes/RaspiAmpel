package net.slintes.raspi;

import com.pi4j.io.gpio.*;

/**
 * Created with IntelliJ IDEA.
 * User: slintes
 * Date: 26.12.12
 * Time: 16:09
 */
public class Ampel {

    private static final Pin PIN_RED = RaspiPin.GPIO_04; // orig 24
    private static final Pin PIN_YELLOW = RaspiPin.GPIO_05; // orig 25
    private static final Pin PIN_GREEN = RaspiPin.GPIO_06; // orig 26
    private static final String NAME_RED = "red";
    private static final String NAME_YELLOW = "yellow";
    private static final String NAME_GREEN = "green";
    private static final int STATE_RED = 1;
    private static final int STATE_YELLOW = 2;
    private static final int STATE_GREEN = 4;

    public static void main(String[] args){
        Ampel ampel = new Ampel();
        ampel.start();
    }

    private void start() {
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput redLED = gpio.provisionDigitalOutputPin(PIN_RED, NAME_RED, PinState.LOW);
        GpioPinDigitalOutput yellowLED = gpio.provisionDigitalOutputPin(PIN_YELLOW, NAME_YELLOW, PinState.LOW);
        GpioPinDigitalOutput greenLED = gpio.provisionDigitalOutputPin(PIN_GREEN, NAME_GREEN, PinState.LOW);

        redLED.setShutdownOptions(true, PinState.LOW);
        yellowLED.setShutdownOptions(true, PinState.LOW);
        greenLED.setShutdownOptions(true, PinState.LOW);

        int durationLong = 2000;
        int durationShort= 800;

        StateAndDuration[] stateAndDurations = {new StateAndDuration(STATE_RED, durationLong),
                new StateAndDuration(STATE_RED + STATE_YELLOW, durationShort),
                new StateAndDuration(STATE_GREEN, durationLong),
                new StateAndDuration(STATE_YELLOW, durationLong)};

        while(true){
            for (int i=0, length=stateAndDurations.length; i<length; i++){

                StateAndDuration stateAndDuration = stateAndDurations[i];

                redLED.setState((stateAndDuration.state & STATE_RED) == STATE_RED);
                yellowLED.setState((stateAndDuration.state & STATE_YELLOW) == STATE_YELLOW);
                greenLED.setState((stateAndDuration.state & STATE_GREEN) == STATE_GREEN);

                try {
                    Thread.sleep(stateAndDuration.duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class StateAndDuration {

        int state;
        int duration;

        StateAndDuration(int state, int duration) {
            this.state = state;
            this.duration = duration;
        }
    }
}
