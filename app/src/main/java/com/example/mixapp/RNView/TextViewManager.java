package com.example.mixapp.RNView;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Map;

public class TextViewManager extends SimpleViewManager<TextView> {

    public static final String REACT_CLASS = "TextView";
    public static final String CLICK_EVENT = "tv_click_event";
    public static final int SET_COLOR_ID = 1;
    public static final String SET_COLOR_NAME = "set_text_color";

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected TextView createViewInstance(@NonNull ThemedReactContext reactContext) {
        TextView textView = new TextView(reactContext);
        return textView;
    }

    @ReactProp(name = "text")
    public void setText(TextView view, String text) {
        view.setText(text);
    }

    /**
     * 手动添加一个事件
     *
     * @return
     */
/*    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.<String, Object>builder()
                .put(CLICK_EVENT, MapBuilder.of("registrationName", "onClick")).build();
    }*/

    /**
     * 直接设置点击事件
     *
     * @param reactContext
     * @param view
     */
    @Override
    protected void addEventEmitters(@NonNull final ThemedReactContext reactContext, @NonNull final TextView view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WritableMap map = Arguments.createMap();
                //这里的key值对应了 RN中TextView组件的点击事件返回的e.nativeEvent对象中的值 e.nativeEvent.message
                map.putString("message", "okay,you click me with addEventEmitters");
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(view.getId(), CLICK_EVENT, map);
            }
        });
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(SET_COLOR_NAME, SET_COLOR_ID);
    }
    // 这里一个情况就是，getCommandsMap接收的 id 是Integer类型，receiveCommand返回的 id 是String类型

    /**
     * RN层对command的处理就是用了UIManager.dispatchViewManagerCommand来处理的，传到原生一个为1的commandID。Native层在接收到相应的commandId去处理对应的任务。
     * 注意[Colors.white]，这里对应的原生的是一个ReadableArray，所以写法就是用[ ],可以写多参数。
     * @param root
     * @param commandId
     * @param args
     */
    @Override
    public void receiveCommand(@NonNull TextView root, String commandId, @Nullable ReadableArray args) {
        switch (Integer.valueOf(commandId)) {
            case SET_COLOR_ID:
                if (args != null) {
                    String color = args.getString(0);
                    root.setTextColor(Color.parseColor(color));
                }
                break;
        }
    }
}
