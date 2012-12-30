package net.slintes.raspiAmpel;

/**
 * Created with IntelliJ IDEA.
 * User: slintes
 * Date: 30.12.12
 * Time: 13:31
 *
 * Ampel (traffic light) implementation just using System.out
 *
 */
public class SysOutAmpel implements Ampel {

    SysOutAmpel(){}

    @Override
    public void setState(State state) {
        System.out.println("ampel: " + state.toString());
    }
}
