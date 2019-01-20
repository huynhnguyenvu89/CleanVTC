package com.vuhuynh.data.cache;

import android.content.Context;
import com.example.domain.executor.ThreadExecutor;
import com.vuhuynh.data.cache.serializer.Serializer;
import com.vuhuynh.data.entity.UserEntity;
import com.vuhuynh.data.exception.UserNotFoundException;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

/**
 * @{link UserCache} implementation
 */
@Singleton
public class UserCacheImpl implements UserCache{

    private static final String DEFAULT_FILE_NAME = "user_";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";
    private static final String SETTINGS_FILE_NAME = "com.vuhuynh.cleanvtc.SETTINGS";
    private static long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final FileManager fileManager;
    private final Serializer serializer;
    private final ThreadExecutor threadExecutor;

    @Inject
    UserCacheImpl(Context context, Serializer serializer, FileManager fileManager, ThreadExecutor threadExecutor){
        if (context == null || serializer == null || fileManager == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }

        this.context = context;
        this.cacheDir = this.context.getCacheDir();
        this.serializer = serializer;
        this.fileManager = fileManager;
        this.threadExecutor = threadExecutor;
    }

    @Override
    public Observable<UserEntity> get(int userId) {
        return Observable.create(emitter -> {
            final File userEntityFile = UserCacheImpl.this.buildFile(userId);
            final String fileContent = UserCacheImpl.this.fileManager.readFileContent(userEntityFile);
            final UserEntity userEntity = UserCacheImpl.this.serializer.deserialize(fileContent, UserEntity.class);

            if (userEntity != null) {
                emitter.onNext(userEntity);
                emitter.onComplete();
            } else {
                emitter.onError(new UserNotFoundException());
            }
        });
    }

    @Override
    public void put(UserEntity userEntity) {
        if (userEntity != null) {
            int userId = userEntity.getUserId();
            File userEntityFile = buildFile(userId);
            if (!isCached(userId)){
                final String jsonString = UserCacheImpl.this.serializer.serialize(userEntity, UserEntity.class);
                executeAsynchronously(new CacheWriter(fileManager, userEntityFile, jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(int userId) {
        final File userEntityFile = UserCacheImpl.this.buildFile(userId);
        return UserCacheImpl.this.fileManager.exists(userEntityFile);
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = UserCacheImpl.this.getLastCacheUpdateTimeMillis();

        boolean expired = (currentTime - lastUpdateTime) > EXPIRATION_TIME;
        if (expired)
            this.evictAll();

        return expired;
    }

    @Override
    public void evictAll() {
        UserCacheImpl.this.executeAsynchronously(new CacheEvictor(fileManager, this.cacheDir));
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param userId the User ID used to build the file.
     * @return A valid file
     */
    private File buildFile(int userId){
        final StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(userId);
        System.out.println("<VH> build file cache dir " + fileNameBuilder.toString());

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis(){
        final long currentTimeMillis = System.currentTimeMillis();
        UserCacheImpl.this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentTimeMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     * @return
     */
    private long getLastCacheUpdateTimeMillis(){
        return UserCacheImpl.this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     * @param runnable {@link Runnable} to execute.
     */
    private void executeAsynchronously(Runnable runnable){
        threadExecutor.execute(runnable);
    }

    private class CacheWriter implements Runnable {
        private FileManager fileManager;
        private File file;
        private String fileContent;

        CacheWriter(FileManager fileManager, File file, String fileContent){
            this.fileManager = fileManager;
            this.file = file;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            fileManager.writeToFile(file, fileContent);
        }
    }

    private class CacheEvictor implements Runnable {
        private FileManager fileManager;
        private File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir){
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }
        @Override
        public void run() {
            fileManager.clearDirectory(this.cacheDir);
        }
    }
}
