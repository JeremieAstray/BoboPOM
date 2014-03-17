package boboPOM.util;

import javafx.scene.media.AudioClip;

public class MusicLoader {

    private AudioClip music;
    
    public MusicLoader(String url){
        music = new AudioClip(url);
    }

    public void playMusic(){
        music.play();
    }

    public void stopMusic(){
        music.stop();
    }
    public void loopMusic(){
        music.setCycleCount(AudioClip.INDEFINITE);
        music.play();
    }

    
}
