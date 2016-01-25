/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interpretador.music;

import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author rsantos34
 */
public class RecAndPlay {
    
    private static Mixer mixer;
    
    public static void main(String[] args) {
        
        //TODO: Ver a lista de Mixer.Info em casa, e procurar pelo resultado da Fast Track.
        //TODO: Capturar o som do canal da Fast Track, e não do drive de som primário. Uma Info da Fast Track deve estar em Mixer.Info
        
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
        
        for (Mixer.Info info : mixInfos) {
            System.out.println("Nome: "+info.getName()+ "\n\rDescrição: "+ info.getDescription()+"\n\r======\n\r");
        }
        
        mixer = AudioSystem.getMixer(mixInfos[0]);
        
        try {
            //DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            //final SourceDataLine sourceLine = (SourceDataLine)AudioSystem.getLine(info);
            final SourceDataLine sourceLine = (SourceDataLine)mixer.getLine(info);
            sourceLine.open();
            
            info = new DataLine.Info(TargetDataLine.class, format);
            final TargetDataLine targetLine = (TargetDataLine)AudioSystem.getLine(info);
            targetLine.open();
            
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            
            Thread sourThread = new Thread(){
                @Override
                public void run(){
                    sourceLine.start();
                    while (true) {                        
                        sourceLine.write(out.toByteArray(), 0, out.size());
                    }
                }
            };
            
            Thread targetThread = new Thread(){
                @Override
                public void run(){
                    sourceLine.start();
                    byte[] data = new byte[targetLine.getBufferSize()/5];
                    int readBytes;
                    while (true) {                        
                        readBytes = targetLine.read(data, 0, data.length);
                        out.write(data, 0, readBytes);
                    }
                }
            };
            
            targetThread.start();
            System.out.println("Started recording...");
            Thread.sleep(5000);
            targetLine.stop();
            targetLine.close();
            
            System.out.println("Ended Recording");
            System.out.println("Starting Playback...");
            
            sourThread.start();
            Thread.sleep(5000);
            sourceLine.stop();
            sourceLine.close();
            System.out.println("Ended Playback");
            
        } catch (LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(RecAndPlay.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        System.out.println("");
    }
    
}
