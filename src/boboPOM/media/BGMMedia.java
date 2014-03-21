package boboPOM.media;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class BGMMedia {

    private Media musicName;
    private MediaPlayer music;
    
    public BGMMedia(String url){
        musicName = new Media(url);
        music = new MediaPlayer(musicName);
    }
    public void playMusic(){
        music.setCycleCount(1);
        music.play();
    }

    public void stopMusic(){
        music.stop();
    }
    public void loopMusic(){
        music.setCycleCount(MediaPlayer.INDEFINITE);
        if(music.getStatus().toString().equals("PLAYING"))
            music.stop();
        music.play();
    }
    public void changeMusic(String url){
        //music.stop();
        musicName = new Media(url);
        music = new MediaPlayer(musicName);
        music.play();
    }
    public Status getStatus(){
        return music.getStatus();
    }
    
}