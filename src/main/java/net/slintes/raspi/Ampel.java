package net.slintes.raspi;

import com.pi4j.io.gpio.*;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: slintes
 * Date: 26.12.12
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public class Ampel {

    private GpioPinDigitalOutput redLED;
    private GpioPinDigitalOutput yellowLED;
    private GpioPinDigitalOutput greenLED;
    private static final String NAME_RED = "red";
    private static final String NAME_YELLOW = "yellow";
    private static final String NAME_GREEN = "green";
    private static final int STATE_OFF = 0;
    private static final int STATE_RED = 1;
    private static final int STATE_YELLOW = 2;
    private static final int STATE_GREEN = 4;

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
    }

    /**
     * Creates an "Ampel" (traffic light)
     * Pin numbers have to match Pi4J GPIO pin numbers,
     * see http://pi4j.com/usage.html#Pin_Numbering
     *
     * @param pinNrRed GPIO pin number for red LED, see http://pi4j.com/usage.html#Pin_Numbering
     * @param pinNrYellow GPIO pin number for yellow LED, see http://pi4j.com/usage.html#Pin_Numbering
     * @param pinNrGreen GPIO pin number for green LED, see http://pi4j.com/usage.html#Pin_Numbering
     */
    public Ampel(int pinNrRed, int pinNrYellow, int pinNrGreen) throws IllegalArgumentException {

        if(!(isValidPinNr(pinNrRed) && isValidPinNr(pinNrYellow) && isValidPinNr(pinNrGreen))){
            throw new IllegalArgumentException("invalid pin nr, must be >= 0 and <= 20, see GPIO pin number, see http://pi4j.com/usage.html#Pin_Numbering");
        }

        if(pinNrRed == pinNrYellow || pinNrRed == pinNrGreen || pinNrYellow == pinNrGreen){
            throw new IllegalArgumentException("pin numbers must be different");
        }

        init(pinNrRed, pinNrYellow, pinNrGreen);
    }

    private boolean isValidPinNr(int pinNr){
        return pinNr >= 0 && pinNr <= 20;
    }

    private void init(int pinNrRed, int pinNrYellow, int pinNrGreen){

        GpioController gpio = GpioFactory.getInstance();
        redLED = gpio.provisionDigitalOutputPin(getPin(pinNrRed), NAME_RED, PinState.LOW);
        yellowLED = gpio.provisionDigitalOutputPin(getPin(pinNrYellow), NAME_YELLOW, PinState.LOW);
        greenLED = gpio.provisionDigitalOutputPin(getPin(pinNrGreen), NAME_GREEN, PinState.LOW);

        redLED.setShutdownOptions(true, PinState.LOW);
        yellowLED.setShutdownOptions(true, PinState.LOW);
        greenLED.setShutdownOptions(true, PinState.LOW);

    }

    private Pin getPin(int pinNr){

        Pin pin = null;

        String pinName = "GPIO_" + (pinNr < 10 ? "0" + pinNr : "" + pinNr);
        try {
            Field field = RaspiPin.class.getDeclaredField(pinName);
            pin = (Pin) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pin;

    }

    /**
     * set the new state
     * will be active until a new state is set
     *
     * @param state
     */
    public void setState(State state){
        redLED.setState((state.value & STATE_RED) == STATE_RED);
        yellowLED.setState((state.value & STATE_YELLOW) == STATE_YELLOW);
        greenLED.setState((state.value & STATE_GREEN) == STATE_GREEN);
    }


}
