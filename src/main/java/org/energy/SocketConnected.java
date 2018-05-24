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
public class SocketConnected {

    private static boolean connected;

    public static boolean isConnected() {
        return connected;
    }

    public static void setStatus(boolean connected) {
        SocketConnected.connected = connected;
    }

}
