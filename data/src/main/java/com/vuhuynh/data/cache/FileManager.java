package com.vuhuynh.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;

/**
 * Helper class to do operations on regular files/directories
 */
@Singleton
public class FileManager {

    @Inject
    FileManager(){ }

    /**
     * Write a file to Disk
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform the operation using another thread.
     *
     * @param file A {@link File} to write to.
     * @param fileContent The file content to be written.
     */
    void writeToFile(File file, String fileContent){
        if (!file.exists()){
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(fileContent);
                fileWriter.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    /**
     * Reads a content from a file.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform the operation using another thread.
     *
     * @param file The file to read from.
     * @return A String with the conant of the file.
     */
    String readFileContent(File file){
        StringBuilder fileContentBuilder = new StringBuilder();
        if(file.exists()) {
            try {
                final FileReader fileReader = new FileReader(file);
                final BufferedReader bufferedReader = new BufferedReader(fileReader);
                String stringLine;
                while ((stringLine = bufferedReader.readLine()) != null) {
                    fileContentBuilder.append(stringLine);
                    fileContentBuilder.append("\n");
                }
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return fileContentBuilder.toString();
    }

    /**
     * Return a boolean indicating whether this file can be found on the underlying file system.
     *
     * @param file The file to check existence.
     * @return true if the file exists, false otherwise.
     */
    boolean exists(File file) {
        return file.exists();
    }

    /**
     * Get a value from a user preferences file.
     *
     * @param context {@link Context} to retrieve android user preferences.
     * @param preferenceFileName a file name representing where data will be retrieved from.
     * @param key a key that will be used to retrieve the value from the preference file.
     * @return A long representing the value retrieved from the preferences file.
     */
    long getFromPreferences(Context context, String preferenceFileName, String key){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0L);
    }

    /**
     * Write a value to a user preferences file.
     *
     * @param context {@link Context} to retrieve android user preferences.
     * @param preferenceFileName a file name representing where data will be retrieved from.
     * @param key a key that will be used to retrieve the data in the future.
     * @param value a Long value to be written in user preferences.
     */
    void writeToPreferences(Context context, String preferenceFileName, String key, long value) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Deletes the content of a directory.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform the operation using another thread.
     *
     * @param dir the {@link File} or directory which its contents will be deleted.
     * @return true if successfully delete the directory and its contents, false otherwise.
     */
    boolean clearDirectory(File dir){
        boolean result = false;
        if (dir.exists()) {
            for (File file : dir.listFiles()){
                result = file.delete();
            }
        }
        return result;
    }
}
