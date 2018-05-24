/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy.devices;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.energy.Main;

/**
 *
 * @author root
 */
public class Devices {

    public class modbusCommands {

        public static final int RD_HOLDING_REG = 0x03;
        public static final int RD_INPUT_REG = 0x04;
        public static final int PRESET_SINGLE_REG = 0x06;
        public static final int PRESET_MULTI_REG = 0x10;
    }

    private static int getCRC(int[] data) {
        int int_crc = 0xFFFF;
        int int_lsb;
        int int_crc_byte_a, int_crc_byte_b;
        for (int int_i = 0; int_i < data.length - 2; int_i++) {
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

    public static void write(int slaveId, int modbusCommand, String device, int register, int regNum) throws IllegalStateException, IOException, ClassNotFoundException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        Class myClass = Class.forName(Devices.class.getPackage().getName() + "." + device);

        int[] sendInt = new int[8];
       // System.out.println(register);
        sendInt[0] = slaveId;
        sendInt[1] = modbusCommand;
        sendInt[2] = register >> 8;
        sendInt[3] = register & 0xff;
        //sendInt[3] = sendInt[3]&0x0f;
        sendInt[4] = regNum >> 8;
        sendInt[5] = regNum & 0xff;
       // System.out.println(sendInt[2]);
       // System.out.println(sendInt[3]);

        byte[] sendByte = new byte[8];
        for (int i = 0; i < 6; i++) {
            sendByte[i] = (byte) sendInt[i];
//            System.out.println(sendByte[i]);
        }
        //System.out.println(getCRC(sendInt) & 0xFF);
        //System.out.println(getCRC(sendInt) >> 8);
        for (int i = 0; i < 6; i++) {
            Main.serial.write(sendByte[i]);
        }

        Main.serial.write((byte) (getCRC(sendInt) & 0xFF));
        Main.serial.write((byte) (getCRC(sendInt) >> 8));
    }

    public static float read(byte[] dataReceive, int type) {
        float f = 0;
        try {
            String myUrl = "jdbc:mysql://localhost/my_db";
            Connection conn = DriverManager.getConnection(myUrl, "root", "1234");
            Statement st = conn.createStatement();

            if (type == 1) {
                int i = (dataReceive[3] & 0xff << 8 | dataReceive[4] & 0xff);
                System.out.println(i);
            } else if (type == 2) {
                byte[] array = {dataReceive[6], dataReceive[5], dataReceive[4], dataReceive[3]};
                f = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                //System.out.println(f);
                //output = f;
                //System.out.println(f);
            } else if (type == 3) {
                long l = ((dataReceive[3] & 0xffL) << 24) | ((dataReceive[4] & 0xffL) << 16) | ((dataReceive[5] & 0xffL) << 8) | (dataReceive[6] & 0xffL);
                System.out.println(l);
            }
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //st.executeUpdate("INSERT INTO data (serverTime,v1) "
            //         + "VALUES ('" + sdf.format(date) + "','" + f + "')"

        } catch (SQLException ex) {
            Logger.getLogger(Devices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

    public static float[] convert(String device, String[] regArray, byte[] buffer) throws ClassNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class myClass = Class.forName(Devices.class.getPackage().getName() + "." + device);
        Field f;
        float output[] = new float[regArray.length];
        for (int i = 0; i < regArray.length; i++) {
            f = myClass.getDeclaredField(regArray[i]);
            int[] reg = (int[]) f.get(myClass);
            int pos = reg[3];
            byte[] array = {buffer[pos + 3], buffer[pos + 2], buffer[pos + 1], buffer[pos]};
        }
        return output;
    }
}
