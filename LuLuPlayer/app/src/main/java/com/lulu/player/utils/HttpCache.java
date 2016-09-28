package com.lulu.player.utils;

import com.lulu.player.app.App;
import com.lulu.player.common.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 缓存
 *
 * @author zxc
 * @time 2016/9/26 0026上午 11:03
 */
public class HttpCache {


    /**
     * 缓存网络数据
     *
     * @param serializable
     * @param file
     * @return
     */
    public static boolean saveObject(Serializable serializable, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = App.getContext().openFileOutput(file, App.getContext().MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(serializable);
            oos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 读取缓存对象
     *
     * @param file
     * @return
     */
    public static Serializable getObject(String file) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = App.getContext().openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 判断是否需要向服务器重新请求数据
     *
     * @param cacheFile
     * @return false 不需要 true 需要
     */
    public static boolean isCacheDataFailure(String cacheFile) {
        boolean failure = false;
        File data = App.getContext().getFileStreamPath(cacheFile);
        if (data.exists() && (System.currentTimeMillis() - data.lastModified()) > Constants.CACHE_TIME) {
            failure = true;
        } else if (!data.exists()) {
            failure = true;
        }
        return failure;
    }

}
