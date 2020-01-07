package com.example.mixapp.RNModule;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

public class ReactToast extends ReactContextBaseJavaModule {

    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";

    public ReactToast(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);

    }

    @NonNull
    @Override
    public String getName() {
        return "ReactToast";
    }

    /**
     * 一个可选的方法getConstants返回了需要导出给JavaScript使用的常量。
     * 它并不一定需要实现，但在定义一些可以被JavaScript同步访问到的预定义的值时非常有用。
     */
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }

    /**
     * 要导出一个方法给JavaScript使用，Java方法需要使用注解@ReactMethod。
     * 方法的返回类型必须为void。React Native的跨语言访问是异步进行的，
     * 所以想要给JavaScript返回一个值的唯一办法是使用回调函数或者发送事件
     *
     * @param message
     * @param duration
     */
    @ReactMethod
    public void show(String message, int duration) {
        Toast.makeText(getReactApplicationContext(), message, duration).show();
    }
}
