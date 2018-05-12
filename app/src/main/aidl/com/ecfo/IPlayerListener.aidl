// IPlayerListener.aidl
package com.ecfo;

// Declare any non-default types here with import statements

interface IPlayerListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void action(in int action , in Message msg);
}
