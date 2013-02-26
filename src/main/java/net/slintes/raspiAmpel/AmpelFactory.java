/*
 * Copyright 2013 Marc Sluiter
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
