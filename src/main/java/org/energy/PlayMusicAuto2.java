/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;

/**
 *
 * @author songo
 */
public class PlayMusicAuto2 implements Runnable {

    public static Clip clip;
    public static String currentSong = "";
    public static String songTmp = "";
    public static ResultSet rs = null;
    private MysqlConfig mysql = new MysqlConfig();

    @Override
    public void run() {
        try {
            int cnt, cntMax, checkBefore;
            Connection conn = mysql.getConn();
            Statement st = conn.createStatement();

            ResultSet rsMode = null;
            String path = "web/audio/converted/";
            Calendar calendarCurrent = Calendar.getInstance();
            Calendar calendarList = Calendar.getInstance();
            Calendar calendarTmp = Calendar.getInstance();
            Calendar calendarMax = Calendar.getInstance();
            clip = AudioSystem.getClip(AudioSystem.getMixerInfo()[0]);
            //operate
            while (true) {
                checkBefore = 0;
                rsMode = st.executeQuery("SELECT * FROM musicmode");
                rsMode.next();
                if (rsMode.getString("state").equals("off")) {
                    if (clip.isOpen()) {
                        clip.close();
                        currentSong = "";
                        songTmp = "";
                    }
                } else {
                    rs = st.executeQuery("SELECT * FROM musicautoch2");
                    cnt = cntMax = 0;
                    while (rs.next()) {
                        String currentTime = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
                        String time = rs.getString("hour") + ":" + rs.getString("min") + " " + rs.getString("period");
                        calendarCurrent.setTime(new SimpleDateFormat("hh:mm a").parse(currentTime));
                        calendarList.setTime(new SimpleDateFormat("hh:mm a").parse(time));
                        if (calendarCurrent.before(calendarList) && checkBefore == 0) {
                            if (cntMax == 0) {
                                calendarMax.setTime(new SimpleDateFormat("hh:mm a").parse(time));
                            }
                            if (calendarMax.after(calendarList) || calendarMax.equals(calendarList)) {
                                if (rs.getString("state").equals("Enabled")) {
                                    System.out.println("aaa");
                                    songTmp = rs.getString("name");
                                } else {
                                    songTmp = "";
                                }
                            }
                            System.out.println("aaa");
                            cntMax++;
                        }
                        if (calendarCurrent.after(calendarList) || calendarCurrent.equals(calendarList)) {
                            checkBefore = 1;
                            if (cnt == 0) {
                                calendarTmp.setTime(new SimpleDateFormat("hh:mm a").parse(time));
                                if (rs.getString("state").equals("Enabled")) {
                                    songTmp = rs.getString("name");
                                } else {
                                    songTmp = "";
                                }
                            }
                            if (calendarTmp.before(calendarList) && rs.getString("state").equals("Enabled")) {
                                songTmp = rs.getString("name");
                            }
//                            if (calendarTmp.before(calendarList) || calendarTmp.equals(calendarList)) {
//                                System.out.println(calendarTmp.getTime());
//                                if (rs.getString("state").equals("Enabled")) {
//                                    songTmp = rs.getString("name");
//                                }
//                            }
                            cnt++;
                            System.out.println(songTmp);
                        }
                    }
                    rsMode.close();
                    rs.close();
                }
                if (!currentSong.equals(songTmp)) {
                    currentSong = songTmp;
                    if (clip.isOpen()) {
                        System.out.println("abc");
                        clip.close();
                    }
                    if (!currentSong.equals("")) {
                        clip.open(AudioSystem.getAudioInputStream(new File(path + currentSong + ".wav")));
                        clip.start();
                        clip.loop(clip.LOOP_CONTINUOUSLY);
                    }
                }
                Thread.sleep(500);
            }
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
        }
    }
}
