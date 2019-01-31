package com.vuhuynh.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import com.vuhuynh.data.entity.UserEntity;
import com.vuhuynh.data.entity.mapper.UserEntityJsonMapper;
import com.vuhuynh.data.exception.NetworkConnectionException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.net.MalformedURLException;
import java.util.List;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    private final UserEntityJsonMapper userEntityJsonMapper;

    /**
     * Constructor of the class
     *
     * @param context {@link Context}
     * @param userEntityJsonMapper {@link UserEntityJsonMapper}
     */
    public RestApiImpl(Context context, UserEntityJsonMapper userEntityJsonMapper) {
        if (context == null || userEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructior parameters cannot be null");
        }
        this.context = context;
        this.userEntityJsonMapper = userEntityJsonMapper;
    }

    @Override
    public Observable<List<UserEntity>> userEntityList() {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    String responseUserEntityList = getUserEntitiesFromApi();
                    if (responseUserEntityList != null) {
                        emitter.onNext(userEntityJsonMapper.transformUserEntityCollection(responseUserEntityList));
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e){
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    @Override
    public Observable<UserEntity> userEntityById(int usedId) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()){
                try {
                    String responseUserDetails = getUserDetailsFromApi(usedId);
                    if (responseUserDetails != null) {
                        emitter.onNext(userEntityJsonMapper.transformUserEntity(responseUserDetails));
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e){
                    emitter.onError(new NetworkConnectionException());
                }
            }
        });
    }

    private String getUserEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(API_URL_GET_USER_LIST).requestSyncCall();
    }

    private String getUserDetailsFromApi(int userId) throws MalformedURLException {
        return ApiConnection.createGET(API_URL_GET_USER_DETAILS + userId + ".json").requestSyncCall();
    }

    private boolean isThereInternetConnection() {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT < 23 && connectivityManager != null) {
            int[] acceptedNetworkTypes = new int[]{ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                for (int networkType : acceptedNetworkTypes) {
                    if (networkInfo.isConnected() && networkType == networkInfo.getType())
                        isConnected = true;
                }
            }
        } else if (connectivityManager != null) {
            int[] acceptedTransports = new int[]{NetworkCapabilities.TRANSPORT_CELLULAR, NetworkCapabilities.TRANSPORT_WIFI};
            final Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                final NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                for (int transport : acceptedTransports) {
                    if (networkCapabilities.hasTransport(transport))
                        isConnected = true;
                }
            }
        }

        return isConnected;
    }

}
