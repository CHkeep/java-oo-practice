package com.twu.domain;

public class User {
    private String username;
    //票数
    private Integer voteChance = 10;

    //管理员
    public static String managerName = "admin";
    public static String password = "admin123";


    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Integer getVoteChance() {
        return voteChance;
    }

    public void setVoteChance(Integer voteChance) {
        this.voteChance = voteChance;
    }
}
