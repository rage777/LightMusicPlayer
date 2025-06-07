// IMusicServiceInterface.aidl
package com.example.lightmusicplayer;

// Declare any non-default types here with import statements
import com.example.lightmusicplayer.IPlayerInfoCallbackInterface;
interface IMusicServiceInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

     void registerCallback(IPlayerInfoCallbackInterface callback);

     void unregisterCallback(IPlayerInfoCallbackInterface callback);

     void addMusicToListAndPlay(String musicInfoJson);

     void play(int index);

     void pause();

     void resume();

     void seekTo(int position);

     void next();

     void previous();

}