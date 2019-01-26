package com.vuhuynh.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.vuhuynh.data.entity.UserEntity;
import com.vuhuynh.data.exception.NetworkConnectionException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.util.List;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi{

    private final Context context;

    public RestApiImpl(Context context){
        this.context = context;
    }

    @Override
    public Observable<List<UserEntity>> userEntityList() {
        return Observable.create(new ObservableOnSubscribe<List<UserEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<UserEntity>> emitter) throws Exception {
                if(isThereInternetConnection()){

                } else {
                    emitter.onError(new NetworkConnectionException());
                }
            }
        });
    }

    @Override
    public Observable<UserEntity> userEntityById(int usedId) {
        return null;
    }

    private boolean isThereInternetConnection(){
        boolean isConnected;
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
        return isConnected;
    }
}
