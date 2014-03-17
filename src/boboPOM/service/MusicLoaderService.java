package boboPOM.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.scene.media.AudioClip;

public class MusicLoaderService {

    AudioClip music; 
    
    MusicLoaderService(String url){
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
