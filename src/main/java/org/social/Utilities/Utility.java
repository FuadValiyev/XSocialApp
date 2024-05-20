package org.social.Utilities;

public class Utility {

    public static IllegalArgumentException IllegalArgException(Object obj) {
        return new IllegalArgumentException(obj +" not found");
    }
}
