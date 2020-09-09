package com.twu.domain;

//热搜 热搜名+热搜热度+超级热搜热度+热搜排名+当前价格
public class HotSpot {
    private String name;
    private Integer hot;
    private Boolean superHot;
    private Integer rank = 0;
    private Integer currentPrice = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public Boolean getSuperHot() {
        return superHot;
    }

    public void setSuperHot(Boolean superHot) {
        this.superHot = superHot;
    }

    public Integer getRank() {
        return rank;
    }

    public Integer getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Integer currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
