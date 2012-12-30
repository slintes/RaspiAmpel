package net.slintes.raspiAmpel;

import com.pi4j.io.gpio.*;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: slintes
 * Date: 26.12.12
 * Time: 16:09
 *
 * Ampel (traffic light) implementation on Raspberry Pi
 *
 */
public class RaspiAmpel implements Ampel {

    private GpioPinDigitalOutput redLED;
    private GpioPinDigitalOutput yellowLED;
    private GpioPinDigitalOutput greenLED;
    private static final String NAME_RED = "red";
    private static final String NAME_YELLOW = "yellow";
    private static final String NAME_GREEN = "green";


    /**
     * Creates an "RaspiAmpel" (traffic light)
     * Pin numbers have to match Pi4J GPIO pin numbers,
     * see http://pi4j.com/usage.html#Pin_Numbering
     *
     * @param pinNrRed GPIO pin number for red LED, see http://pi4j.com/usage.html#Pin_Numbering
     * @param pinNrYellow GPIO pin number for yellow LED, see http://pi4j.com/usage.html#Pin_Numbering
     * @param pinNrGreen GPIO pin number for green LED, see http://pi4j.com/usage.html#Pin_Numbering
     */
    RaspiAmpel(int pinNrRed, int pinNrYellow, int pinNrGreen) throws IllegalArgumentException {

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

    @Override
    public void setState(State state){
        redLED.setState((state.value & STATE_RED) == STATE_RED);
        yellowLED.setState((state.value & STATE_YELLOW) == STATE_YELLOW);
        greenLED.setState((state.value & STATE_GREEN) == STATE_GREEN);
    }


}