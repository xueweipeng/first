// IPlayer.aidl
package com.ecfo;
import com.ecfo.IPlayerListener;

// Declare any non-default types here with import statements

interface IPlayer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void action(in int action ,in String datum);
    void registerListener(IPlayerListener listener);
    void unregisterListener(IPlayerListener listener);
    Message getCurrentSongInfo();
}
