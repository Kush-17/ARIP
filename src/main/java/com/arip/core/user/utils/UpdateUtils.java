package com.arip.core.user.utils;

import java.util.function.Consumer;

public class UpdateUtils {

    public static <T> void applyIfNotNull(T value, Consumer<T> setter){
        if(value != null) {
            setter.accept(value);
        }
    }
}
