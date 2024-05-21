package org.social.utilities;

public class Utility {

    public static IllegalArgumentException IllegalArgException(Object obj) {
        return new IllegalArgumentException(obj +" not found");
    }
}
