package com.example.mixapp.RNModule;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReactToast extends ReactContextBaseJavaModule {

    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";
    private ReactContext reactContext;
    private Promise promise;

    private final ActivityEventListener activityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            super.onActivityResult(activity, requestCode, resultCode, data);
            if (requestCode == 0) {
                if (promise != null) {
                    if (resultCode == Activity.RESULT_OK) {
                        String result = data.getStringExtra("result");
                        promise.resolve(result);
                    } else {
                        promise.reject("failed", "failed");
                    }
                }
            }
        }
    };

    public ReactToast(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addActivityEventListener(activityEventListener);
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

    /**
     * WritableMap一般是用于从原生传给rn的数据类型
     * ReadableMap一般是用于rn传向原生时候的数据类型
     *
     * @param message
     * @param callback
     */
    @ReactMethod
    public void showBoolean(String message, Callback callback) {
        WritableMap map = Arguments.createMap();

        if (message.equals("1") || message.equals("true")) {
            map.putString("success", "输入的是1呀");
            callback.invoke(map);
        } else {
            map.putString("success", "输入的不是1呀");
            callback.invoke(map);
        }
    }

    @ReactMethod
    public void usePromise(String message, Promise promise) {
        if (TextUtils.isEmpty(message)) {
            WritableMap map = Arguments.createMap();
            map.putString("content", "收到的消息为空");
            promise.resolve(map);
        } else {
            WritableMap map = Arguments.createMap();
            map.putString("content", "收到的消息为空:" + message);
            promise.resolve(map);
        }
    }

    @ReactMethod
    public void sendEvent() {
        WritableMap map = Arguments.createMap();
        map.putString("content", "消息来自event");
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("toRN", map);
    }

    @ReactMethod
    public void startActivity(String name, Promise promise) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            promise.reject("noActivity", "页面不存在");
            return;
        }
        this.promise = promise;

        try {
            Class toActivity = Class.forName(name);
            Intent intent = new Intent(currentActivity, toActivity);
            currentActivity.startActivityForResult(intent, 0);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
