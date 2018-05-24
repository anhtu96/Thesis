/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author songo
 */
public class PlayMusicAuto1 implements Runnable {

    public static Clip clip;
    public static String currentSong = "";
    public static String songTmp = "";
    public static ResultSet rs = null;
    private ResultSet rsMode = null;
    private MysqlConfig mysql = new MysqlConfig();
    private int cnt, cntMax, checkBefore;
    private String path = "web/audio/converted/";
    private Statement st = null;
    private Connection conn = null;
    private Calendar calendarCurrent = Calendar.getInstance();
    private Calendar calendarList = Calendar.getInstance();
    private Calendar calendarTmp = Calendar.getInstance();
    private Calendar calendarMax = Calendar.getInstance();

    @Override
    public void run() {
        conn = mysql.getConn();
        try {
            st = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(PlayMusicAuto1.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clip = AudioSystem.getClip(AudioSystem.getMixerInfo()[0]);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(PlayMusicAuto1.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
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
                    rs = st.executeQuery("SELECT * FROM musicautoch1");
                    cnt = cntMax = 0;
                    while (rs.next()) {
                        System.out.println(rs.getInt("state"));
                        String currentTime = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
                        String time = rs.getString("hour") + ":" + rs.getString("min") + " " + rs.getString("period");
                        calendarCurrent.setTime(new SimpleDateFormat("hh:mm a").parse(currentTime));
                        calendarList.setTime(new SimpleDateFormat("hh:mm a").parse(time));
                        if (calendarCurrent.before(calendarList) && checkBefore == 0 && rs.getInt("state") == 1) {
                            if (calendarMax.before(calendarList) || cntMax == 0) {
                                calendarMax.setTime(calendarList.getTime());
//                                    System.out.println("aaa");
                                songTmp = rs.getString("name");
                            }
                            cntMax++;
                        }
                        if ((calendarCurrent.after(calendarList) || calendarCurrent.equals(calendarList)) && rs.getInt("state") == 1) {
                            checkBefore = 1;
                            if (calendarTmp.before(calendarList) || cnt == 0) {
                                calendarTmp.setTime(calendarList.getTime());
                                songTmp = rs.getString("name");
                            }
//                            if (calendarTmp.before(calendarList) || calendarTmp.equals(calendarList)) {
//                                System.out.println(calendarTmp.getTime());
//                                if (rs.getString("state").equals("Enabled")) {
//                                    songTmp = rs.getString("name");
//                                }
//                            }
                            cnt++;
                        }
                    }
                    if (cnt == 0) {
                        songTmp = "";
                    }
                    rsMode.close();
                    rs.close();
                }
                if (!currentSong.equals(songTmp)) {
                    currentSong = songTmp;
                    if (clip.isOpen()) {
//                        System.out.println("abc");
                        clip.close();
                    }
                    if (!currentSong.equals("")) {
                        clip.open(AudioSystem.getAudioInputStream(new File(path + currentSong + ".wav")));
                        clip.start();
                        clip.loop(clip.LOOP_CONTINUOUSLY);
                    }
                }
                Thread.sleep(5000);
                System.out.println(songTmp);
            } catch (SQLException ex) {
                Logger.getLogger(PlayMusicAuto1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(PlayMusicAuto1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(PlayMusicAuto1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PlayMusicAuto1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(PlayMusicAuto1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(PlayMusicAuto1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
