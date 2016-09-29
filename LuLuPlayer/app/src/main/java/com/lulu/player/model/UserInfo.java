package com.lulu.player.model;

import com.google.gson.annotations.SerializedName;

/**
 * 业务具体处理，包括负责存储、检索、操纵数据等
 * 用户信息
 *
 * @author zxc
 * @time 2016/9/22 0022 下午 5:57
 */
public class UserInfo<T> {

    @SerializedName("status")
    private int status;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;

    @SerializedName("level")
    private int level;

    @SerializedName("levels")
    private T levels;
    //private List<Levels> levels;
//    private Levels[] levels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getLevels() {
        return levels;
    }

    public void setLevels(T levels) {
        this.levels = levels;
    }
}
