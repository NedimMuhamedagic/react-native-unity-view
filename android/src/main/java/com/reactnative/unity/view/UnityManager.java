package com.reactnative.unity.view;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Promise;

public class UnityManager extends ReactContextBaseJavaModule implements UnityEventListener {

    public UnityManager(ReactApplicationContext reactContext) {
        super(reactContext);
        UnityUtils.addUnityEventListener(this);
    }

    @Override
    public String getName() {
        return "UnityManager";
    }

    @ReactMethod
    public void pause() {
        UnityUtils.getPlayer().pause();
    }

    @ReactMethod
    public void resume() {
        UnityUtils.getPlayer().resume();
    }

    @ReactMethod
    public void isReady(Promise promise) {
        promise.resolve(UnityUtils.isUnityReady());
    }

    @ReactMethod
    public void createUnity(final Promise promise) {
        UnityUtils.createPlayer(getCurrentActivity(), new UnityUtils.CreateCallback() {
            @Override
            public void onReady() {
                promise.resolve(true);
            }
        });
    }

    @Override
    public void onMessage(String message) {
        WritableMap infoMap = Arguments.createMap();
        infoMap.putString("message", message);
        this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("UnityEvent", infoMap);
    }

    @ReactMethod
    public void postMessage(String gameObject, String methodName, String message)
    {
        UnityUtils.postMessage(gameObject, methodName, message);
    }

}
