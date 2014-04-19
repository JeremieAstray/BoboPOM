package boboPOM.media;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author:feng
 */

public class BGMMedia {

    private Media music;
    private MediaPlayer musicPlayer;

    private int playingMusic = -1; //表示当前播放音乐，-1表示当前无音乐

    private String musicPath;
    private String[] musicNames;

    public BGMMedia(String path) {
        musicPath = path;
        URI uri = null;
        try {
            uri = new URI(path);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        File filePath = new File(uri);

        File[] files = filePath.listFiles();
        musicNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            musicNames[i] = files[i].getName();
        }
    }

    public void stopMusic() {
        musicPlayer.stop();
    }

    private void play(boolean loop, int num) {
        if (playingMusic != -1) {
            this.musicPlayer.stop();
        }
        if (playingMusic != num) {
            this.playingMusic = num;
            this.music = new Media(musicPath + "/" + musicNames[num]);
            this.musicPlayer = new MediaPlayer(this.music);
        }
        if (loop) {
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } else {
            musicPlayer.setCycleCount(1);
        }
        musicPlayer.play();

    }

    public boolean playMusic(int music, boolean loop) {
        if (music >= 0 && music < musicNames.length) {
            play(loop, music);
            return true;
        } else {
            return false;
        }
    }

    public boolean playMusic(String music, boolean loop) {
        boolean isExist = false;
        for (int i = 0; i < musicNames.length; i++) {
            if (musicNames[i].equals(music)) {
                isExist = true;
                play(loop, i);
                break;
            }
        }
        return isExist;
    }

    public Status getStatus() {
        return musicPlayer.getStatus();
    }

}
