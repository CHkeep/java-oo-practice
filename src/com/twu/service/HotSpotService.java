package com.twu.service;

import com.twu.domain.HotSpot;
import com.twu.domain.User;
import com.twu.repository.HotSpotRepository;


import java.util.List;

public class HotSpotService {
    public static HotSpotService getHotSpotService() {
        return hotSpotService;
    }
    private static HotSpotService hotSpotService = new HotSpotService();

    private static HotSpotRepository hotSpotRepository = HotSpotRepository.getHotSpotRepository();

    // 添加热搜
    public HotSpot add(String name) {
        return this.add(name, Boolean.FALSE);
    }

    // 添加超级热搜
    public HotSpot add(String name, Boolean superHot) {
        List<HotSpot> hotSpots = hotSpotRepository.findAll();
        // TODO: 2020/9/7 根据情况校验 name 是否已经存在，避免重复添加
        for (HotSpot hotSpot :hotSpots) {
            if (hotSpot.getName().equals(name)) return null;
        }
        HotSpot hotSpot = new HotSpot();
        hotSpot.setHot(0);
        hotSpot.setName(name);
        hotSpot.setRank(hotSpots.size() + 1);
        hotSpot.setSuperHot(superHot);
        hotSpot.setCurrentPrice(0);
        return hotSpotRepository.create(hotSpot);
    }

    //判断该热搜是否存在
    public HotSpot isadd(String name){
        HotSpot hotSpot = hotSpotRepository.findOne(name);
        return hotSpot;
    }

    //给热搜事件投票
    public HotSpot addHot(String name, User user, Integer voteNumer ) {
        // TODO: 2020/9/7 检查剩余票数够不够，减剩余可用投票什么的
        HotSpot hotSpot = hotSpotRepository.findOne(name);

        if (hotSpot.getSuperHot()) {
            hotSpot.setHot(hotSpot.getHot() + voteNumer *2);
            user.setVoteChance(user.getVoteChance()-voteNumer);
        } else {
            hotSpot.setHot(hotSpot.getHot() + voteNumer*1);
            user.setVoteChance(user.getVoteChance()-voteNumer);
        }
        return hotSpotRepository.update(hotSpot);
    }
}
