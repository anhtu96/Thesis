/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author songo
 */
public class GenerateMusicList implements Runnable {

    @Override
    public void run() {
        try {
            Converter conv = new Converter();
            FileWriter filewrite = null;
            File folder = new File("web/audio");
            File[] listOfFiles = folder.listFiles();
            ObjectMapper mapper = null;
            ArrayNode array = null;
            while (true) {
                filewrite = new FileWriter("web/music.json");
                mapper = new ObjectMapper();
                array = mapper.createArrayNode();
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].getName().endsWith(".mp3")) {
                        ObjectNode obj = mapper.createObjectNode();
                        String name = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length() - 4);
                        if (!new File("web/audio/converted/" + name + ".wav").isFile()) {
                            conv.convert("web/audio/" + name + ".mp3", "web/audio/converted/" + name + ".wav");
                        }
                        obj.put("name", name);
                        String path = "audio/" + name + ".mp3";
                        obj.put("url", path);
                        array.add(obj);
                    }
                }
                filewrite.write(array.toString());
                filewrite.flush();
                Thread.sleep(2000);
            }
        } catch (IOException ex) {
            Logger.getLogger(GenerateMusicList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(GenerateMusicList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GenerateMusicList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
