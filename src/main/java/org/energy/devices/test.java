/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy.devices;

import java.lang.reflect.Field;
import static org.eclipse.jetty.server.ShutdownMonitor.register;

/**
 *
 * @author songo
 */
public class test {

    public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        Class myclass = Class.forName(Devices.class.getPackage().getName() + "." + "PM710");
        String[] regArray = {"VOLT_AN", "CURRENT_A", "REAL_POW_A", "REAL_ENERGY_TOTAL"};
        Field f;
        for (int i = 0; i < regArray.length; i++) {
            f = myclass.getDeclaredField(regArray[i]);
            int[] reg = (int[]) f.get(myclass);
            System.out.println(reg[3]);
        }
    }

}
