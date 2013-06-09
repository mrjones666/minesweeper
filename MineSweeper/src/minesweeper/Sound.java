package minesweeper;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    
    private boolean mute = false;
    private String folder, filename, extension;
    
    public Sound() {
        this.folder = "./media/audio/";
        this.extension = "wav";
    }
    
    public Sound(String filename) {
        this.filename = filename;
        this.folder = "./media/audio/";
        this.extension = "wav";
    }

    public Sound(String folder, String extension) {
        this.folder = folder;
        this.extension = extension;
    }
    
    public void play() {
        try {
            AudioInputStream stream =
                    AudioSystem.getAudioInputStream(
                    new File(this.folder + this.filename + "." + this.extension));

            DataLine.Info info =
                    new DataLine.Info(Clip.class,
                    stream.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);

            clip.open(stream);
            clip.start();

        } 
        
        catch (LineUnavailableException e)    
        {
            System.out.println(e.getMessage());
        }
            
        catch (IOException e)    
        {
            System.out.println(e.getMessage());
        }
                        
        catch (UnsupportedAudioFileException e) 
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public void setMute(boolean value) {
        this.mute = value;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    
}
