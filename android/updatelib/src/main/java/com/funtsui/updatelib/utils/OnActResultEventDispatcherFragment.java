package com.funtsui.updatelib.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

public class OnActResultEventDispatcherFragment extends Fragment {
    public static final String TAG = "on_act_result_event_dispatcher";

    private SparseArray<ActForResultCallback> mCallbacks = new SparseArray<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startForResult(Intent intent, ActForResultCallback callback) {
        int key = callback.hashCode() & 0x0000ffff;
        mCallbacks.put(key, callback);
        startActivityForResult(intent, key);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ActForResultCallback callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);

        if (callback != null) {
            callback.onActivityResult(resultCode, data);
        }
    }
}