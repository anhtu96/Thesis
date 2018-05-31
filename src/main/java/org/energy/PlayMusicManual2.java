/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author songo
 */
public class PlayMusicManual2 implements Runnable {

    public static Clip clip;
    public static String currentSong = "";
    private MysqlConfig mysql = new MysqlConfig();

    @Override
    public void run() {
        try {
            Connection conn = mysql.getConn();
            Statement st = conn.createStatement();
            ResultSet rs = null;
            String path = "web/audio/converted/";

            clip = AudioSystem.getClip(AudioSystem.getMixerInfo()[3]);
            while (true) {
                rs = st.executeQuery("SELECT * FROM musicmanual2");
                if (!rs.isBeforeFirst()) {
                    currentSong = "";
                    if (clip.isOpen()) {
                        clip.close();
                    }
                } else {
                    while (rs.next()) {
                        if (!currentSong.equals(rs.getString("name"))) {
                            currentSong = rs.getString("name");
                            if (clip.isOpen()) {
                                clip.close();
                            }
                            clip.open(AudioSystem.getAudioInputStream(new File(path + currentSong + ".wav")));
                            clip.start();
                            clip.loop(clip.LOOP_CONTINUOUSLY);
                        }

                    }
                    rs.close();
                }

                Thread.sleep(500);
            }
        } catch (Exception ex) {
        }
    }
}
