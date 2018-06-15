/*
 * Copyright 2012 - 2015 Anton Tananaev (anton.tananaev@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.energy;

import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPort;
import com.pi4j.io.serial.StopBits;
import java.sql.Connection;
import java.sql.ResultSet;
import org.energy.helper.Log;

import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Locale;

public final class Main {

    private static final long CLEAN_PERIOD = 24 * 60 * 60 * 1000;
    private static MysqlConfig mysql = new MysqlConfig();
    private static Connection conn = mysql.getConn();

    public static final Serial serial = SerialFactory.createInstance();
    public static SerialConfig config = new SerialConfig();

    private Main() {
    }

    public static void main(String[] args) throws Exception {
        GlobalVars.setCheckLight(0);
        GlobalVars.setCheckTemp(0);
        Locale.setDefault(Locale.ENGLISH);

        Context.init(args);

        Log.info("Starting server...");

        //    Context.getServerManager().start();
        if (Context.getWebServer() != null) {
            Context.getWebServer().start();
        }

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                /*try {
                    Context.getDataManager().clearPositionsHistory();
                } catch (SQLException error) {
                    Log.warning(error);
                }*/
            }
        }, 0, CLEAN_PERIOD);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Log.info("Shutting down server...");

                if (Context.getWebServer() != null) {
                    Context.getWebServer().stop();
                }
//                Context.getServerManager().stop();
            }
        });

        config.device(SerialPort.getDefaultPort())
                .baud(Baud._9600)
                .dataBits(DataBits._8)
                .parity(Parity.NONE)
                .stopBits(StopBits._1)
                .flowControl(FlowControl.NONE);
        serial.open(config);
        Statement st = conn.createStatement();
        ResultSet rs = null;
        //reset manual playback mode
        st.execute("truncate table musicmanual1");
        st.execute("truncate table musicmanual2");
        st.execute("truncate table serversession");
        st.close();
//        serial.addListener(new SerialDataEventListener() {
//            @Override
//            public void dataReceived(SerialDataEvent event) {
//                try {
//                    byte[] data = event.getBytes();
//                    for (int i = 0; i < data.length; i++) {
//                        System.out.println(data[i]);
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//        serial.write("AT+B9600");
//        Thread.sleep(500);
//        serial.write("AT+C002");
//        Thread.sleep(500);
//        serial.write("AT+A002");
//        Thread.sleep(500);
//        serial.write("AT+FU1");

        Thread audio1 = new Thread(new PlayMusicAuto1());
        audio1.start();
        Thread audio2 = new Thread(new PlayMusicAuto2());
        audio2.start();
        Thread manual1 = new Thread(new PlayMusicManual1());
        manual1.start();
        Thread manual2 = new Thread(new PlayMusicManual2());
        manual2.start();
        Thread generateMusic = new Thread(new GenerateMusicList());
        generateMusic.start();
        Thread tempControl = new Thread(new TempControl());
        tempControl.start();
        Thread tempSensor = new Thread(new Sensor());
        tempSensor.start();
        Thread lightControl = new Thread(new LightControl());
        lightControl.start();
//        Thread flame = new Thread(new Flame());
//        flame.start();
    }
}
