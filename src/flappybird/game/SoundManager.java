package flappybird.game;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager {
    
    private Clip jumpSound;
    private Clip dieSound;

    private void setVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    public SoundManager() {
        try {
            AudioInputStream jumpAudio = AudioSystem.getAudioInputStream(new File("resources/sounds/jump.wav"));
        
            AudioInputStream dieAudio = AudioSystem.getAudioInputStream(new File("resources/sounds/die.wav"));

            jumpSound = AudioSystem.getClip();
            dieSound = AudioSystem.getClip();

            jumpSound.open(jumpAudio);
            dieSound.open(dieAudio);

            setVolume(jumpSound, -10.0f);
            setVolume(dieSound, -10.0f);
        } catch (Exception e) {
            System.out.println("Khong the load sound" + e.getMessage());
        }
    }

    public void playJump(){
        if(jumpSound != null){
            jumpSound.setFramePosition(0);
            jumpSound.start();
        }
    }

    public void playDie(){
        if(dieSound != null){
            dieSound.setFramePosition(0);
            dieSound.start();
        }
    }
}
