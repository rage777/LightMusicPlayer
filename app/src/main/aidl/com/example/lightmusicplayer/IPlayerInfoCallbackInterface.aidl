// IPlayerInfoCallbackInterface.aidl
package com.example.lightmusicplayer;

// Declare any non-default types here with import statements

interface IPlayerInfoCallbackInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

     void onEvent(String infoJson);

}