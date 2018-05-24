/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

/**
 *
 * @author songo
 */
public class Checksum {

    public int getResult(byte[] data, int size) {
        int int_crc = 0xFFFF;
        int int_lsb;
        int int_crc_byte_a, int_crc_byte_b;
        for (int int_i = 0; int_i < size; int_i++) {
            int_crc = int_crc ^ data[int_i];
            for (int int_j = 0; int_j < 8; int_j++) {
                int_lsb = int_crc & 0x0001; // Mask of LSB
                int_crc = int_crc >> 1;
                int_crc = int_crc & 0x7FFF;
                if (int_lsb == 1) {
                    int_crc = int_crc ^ 0xA001;
                }
            }
        }
        return int_crc;
    }

}
