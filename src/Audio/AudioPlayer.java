package Audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class AudioPlayer {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    //Create the audio player
    public static int MENU_1 = 0;
    public static int LEVEL_1 = 1;
    public static int LEVEL_2 = 2;
    //Create the audio player
    public static int DIE = 0;
    public static int JUMP = 1;
    public static int GAMEOVER = 2;
    public static int LEVEL_COMPLETED = 3;
    public static int ATTACK_1 = 4;
    public static int ATTACK_2 = 5;
    public static int ATTACK_3 = 6;
    //Stored
    private Clip[] songs, effects;
    private int currentSongId;
    private float volume = 0.5f;
    private boolean songMute, effectMute;
    private Random random = new Random();

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public AudioPlayer (){
        loadSongs();
        loadEffects();
        playSong(MENU_1);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load songs
    private void loadSongs(){
        String [] songNames = {"menu", "level1", "level2"};
        songs = new Clip[songNames.length];

        for (int i = 0; i < songNames.length; i++){
            try{
                songs[i] = getClip(songNames[i]);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
                e.printStackTrace();
            }
        }

    }

    //Load effects
    private void loadEffects(){
        String [] effectNames = {"die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3"};
        effects = new Clip[effectNames.length];

        for (int i = 0; i < effectNames.length; i++){
            try{
                effects[i] = getClip(effectNames[i]);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
                e.printStackTrace();
            }
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    private void updateSongVolume(){
        if (songMute){
            songs[currentSongId].stop();
        } else {
            FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }

    }

    private void updateEffectVolume(){
        if (effectMute){
            for (Clip clip : effects){
                clip.stop();
            }

        } else {
            for (Clip clip : effects){
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * volume) + gainControl.getMinimum();
                gainControl.setValue(gain);
            }

        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Toggle
    public void toggleSongMute(){
        this.songMute = !songMute;
        for (Clip clip: songs){
            BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }

    }

    public void toggleEffectMute(){
        this.songMute = !effectMute;
        for (Clip clip: effects){
            BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }

        if (effectMute){
           playEffect(JUMP);
        }

    }

    //Support
    public void playEffect (int effect){
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }

    public void playSong (int song){
        stopSong();

        currentSongId = song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0);
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Play
    public void playAttackSound(){
        int start = 4;
        start += random.nextInt(3);
        playEffect(start);

    }

    public void playLevelCompleted(){
        stopSong();
        playEffect(LEVEL_COMPLETED);

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Stop
    public void stopSong(){
        if (songs[currentSongId].isActive()){
            songs[currentSongId].stop();
        }

    }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    private Clip getClip (String name)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL url = getClass().getResource("/Texture/Audio/" + name + ".wav");
        AudioInputStream audioInputStream = null;

        try{
            audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void setVolume(float volume){
        this.volume = volume;
        updateSongVolume();
        updateEffectVolume();
    }

    public void setLevelSong (int levelIndex){
        if (levelIndex == 0){
            playSong(LEVEL_1);
        } else {
            playSong(LEVEL_2);
        }
    }

}
