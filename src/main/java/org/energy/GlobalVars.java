/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

/**
 *
 * @author root
 */
public class GlobalVars {

    private static int checkTemp;
    private static int checkLight;

    public static int getCheckTemp() {
        return checkTemp;
    }

    public static void setCheckTemp(int checkTemp) {
        GlobalVars.checkTemp = checkTemp;
    }

    public static int getCheckLight() {
        return checkLight;
    }

    public static void setCheckLight(int checkLight) {
        GlobalVars.checkLight = checkLight;
    }

}
