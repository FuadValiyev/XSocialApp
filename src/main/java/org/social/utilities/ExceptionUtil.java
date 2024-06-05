package org.social.utilities;

public class ExceptionUtil {

    public static IllegalArgumentException IllegalArgException(Object obj) {
        return new IllegalArgumentException(obj +" not found");
    }
}
