package com.twu.repository;

import com.twu.domain.HotSpot;

import java.util.ArrayList;
import java.util.List;

public class HotSpotRepository {
    private static HotSpotRepository hotSpotRepository = new HotSpotRepository();
    public static HotSpotRepository getHotSpotRepository() {
        return hotSpotRepository;
    }


    private static List<HotSpot> HOT_SPORTS = new ArrayList<>();

    public List<HotSpot> findAll() {
        return HOT_SPORTS;
    }

   //查找热搜（热搜名）
    public HotSpot findOne(String name) {
        for (HotSpot hotSpot : HOT_SPORTS) {
            if (hotSpot.getName().equals(name)) {
                return hotSpot;
            }
        }
        return null;
    }

    //查找热搜（热搜排名）
    public HotSpot findRank(Integer rank) {
        for (HotSpot hotSpot : HOT_SPORTS) {
            if (hotSpot.getRank().equals(rank)) {
                return hotSpot;
            }
        }
        return null;
    }

    //添加热搜
    public HotSpot create(HotSpot hotSpot) {
        HOT_SPORTS.add(hotSpot);
        return hotSpot;
    }

    // 判断是否存在该热搜，不存在添加，存在更新热搜信息
    public HotSpot update(HotSpot hotSpot) {
        HotSpot data = this.findOne(hotSpot.getName());
        if (data == null) {
            HOT_SPORTS.add(hotSpot);
        } else {
            data.setHot(hotSpot.getHot());
            data.setRank(hotSpot.getRank());
            data.setName(hotSpot.getName());
            data.setSuperHot(hotSpot.getSuperHot());
            data.setCurrentPrice(hotSpot.getCurrentPrice());
        }
        return hotSpot;
    }
}
