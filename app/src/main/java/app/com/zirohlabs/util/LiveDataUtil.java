package app.com.zirohlabs.util;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataUtil {

    public static <T> T getData(LiveData<T> liveData) throws InterruptedException {
        final Object[] result = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T t) {
                result[0] = t;
                Log.i("gsgsfvvv",t!=null ? "Data found":"Not found");
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2,TimeUnit.SECONDS);
        return (T)result[0];
    }
}
