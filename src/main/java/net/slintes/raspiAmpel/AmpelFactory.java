package net.slintes.raspiAmpel;

/**
 * Created with IntelliJ IDEA.
 * User: slintes
 * Date: 30.12.12
 * Time: 13:37
 *
 * Factory for ampel
 */
public class AmpelFactory {

    /**
     * Creates an "RaspiAmpel" (traffic light)
     * Pin numbers have to match Pi4J GPIO pin numbers,
     * see http://pi4j.com/usage.html#Pin_Numbering
     *
     * @param pinNrRed GPIO pin number for red LED, see http://pi4j.com/usage.html#Pin_Numbering
     * @param pinNrYellow GPIO pin number for yellow LED, see http://pi4j.com/usage.html#Pin_Numbering
     * @param pinNrGreen GPIO pin number for green LED, see http://pi4j.com/usage.html#Pin_Numbering
     *
     * @return the ampel
     */
    public static Ampel createAmpel(int pinNrRed, int pinNrYellow, int pinNrGreen){

        Ampel ampel;

        try {
            ampel = new RaspiAmpel(pinNrRed, pinNrYellow, pinNrGreen);
        } catch (Throwable t){
            // fails when not on a Raspberry Pi
            ampel = new SysOutAmpel();
        }

        return ampel;
    }
}
