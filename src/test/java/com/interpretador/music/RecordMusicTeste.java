/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interpretador.music;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author rsantos34
 */
public class RecordMusicTeste {

    private static Mixer mixer;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Starting sound test...");

        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();

        int indice = 0;

        for (Mixer.Info mixInfo : mixInfos) {
            System.out.println("Índice: " + indice);
            System.out.println("Fabricante: "+mixInfo.getVendor());
            System.out.println("Versão: "+mixInfo.getVersion());
            System.out.println("Nome: "+mixInfo.getName());
            System.out.println("Descrição: "+mixInfo.getDescription()+"\r==================================================================\r");
            
            indice = indice+1;
        }
        
        mixer = AudioSystem.getMixer(mixInfos[2]);

        if (!mixer.isLineSupported(info)) {
            System.out.println("Linha não suportada! :c");
        }

        Control[] controles = mixer.getControls();
        for (Control controle : controles) {
            
            System.out.println("\n\rTipo do controle: "+controle.getType().toString());
        }
        
        try {
            //final TargetDataLine targetLine = (TargetDataLine)AudioSystem.getLine(info);
            System.out.println("Informações da linha usada anteriormente: " + AudioSystem.getLine(info));
            
            final TargetDataLine targetLine = (TargetDataLine) mixer.getLine(info);
            targetLine.open();

            System.out.println("Starting recording...");
            targetLine.start();

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        AudioInputStream audioStream = new AudioInputStream(targetLine);
                        File audioFile = new File("gravacao.wav");
                        AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, audioFile);
                        System.out.println("Stopped Recording...");
                    } catch (IOException ex) {
                        Logger.getLogger(RecordMusicTeste.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };

            thread.start();
            Thread.sleep(5000);
            targetLine.stop();
            targetLine.close();

            System.out.println("Ended sound test");

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
            Logger.getLogger(RecordMusicTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
