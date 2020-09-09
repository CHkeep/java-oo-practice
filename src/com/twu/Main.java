package com.twu;

import com.twu.domain.HotSpot;
import com.twu.domain.User;
import com.twu.repository.HotSpotRepository;
import com.twu.service.HotSpotService;

import java.util.*;

public class Main {
    private static HotSpotService hotSpotService;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        init();
        while (true) {
            System.out.println("1.用户\n2.管理员\n3.退出");
            Integer isUser = readCommand();
            if(isUser == 3){
                break;
            }

            switch (isUser) {
                case 1 : {
                    System.out.println("请输入您的昵称：");
                    String username = readName();
                    User user =new User(username);
                    int command ;
                    do{
                        System.out.println("你好，"+username+"，你可以：\n" +
                                "1.查看热搜排行榜\n" +
                                "2.给热搜事件投票\n" +
                                "3.购买热搜\n" +
                                "4.添加热搜\n" +
                                "5.退出");
                        command = readCommand();
                        switch (command) {
                            case 1: {
                                getRankedHotSpot();
                                break;
                            }
                            case 2: {
                                addHotSpot(user);
                                break;
                            }
                            case 3: {
                                System.out.println("购买热搜");
                                hotSpotPay();
                                break;
                            }
                            case 4: {
                                System.out.println("请输入你要添加的热搜事件的名字：");
                                String name = readName();
                                HotSpot hotSpot = hotSpotService.add(name);
                                if (hotSpot == null) {
                                    System.out.println(name + "热搜已存在于热搜列表中！");
                                } else {
                                    System.out.println(name + "热搜添加成功");
                                }
                                break;
                            }
                            default:
                                break;
                        }
                    }while (command != 5);
                    break;
                }
                case 2 : {
                    System.out.println("请输入您的昵称：");
                    String managerName = readName();
                    System.out.println("请输入您的密码：");
                    String password = readName();
                    if(!(isManager(managerName, password))) {System.out.println("用户名和密码错误");break;}
                    int command ;
                    do {
                        System.out.println("你好，"+ managerName+"，你可以：\n" +
                                "1.查看热搜排行榜\n" +
                                "2.添加热搜\n" +
                                "3.添加超级热搜\n" +
                                "4.退出");
                        command = readCommand();
                        switch (command){
                            case 1: {
                                getRankedHotSpot();
                                break;
                            }
                            case 2: {
                                System.out.println("请输入你要添加的热搜事件的名字：");
                                String name = readName();
                                HotSpot hotSpot = hotSpotService.add(name);
                                if (hotSpot == null) {
                                    System.out.println(name + "热搜已经存在");
                                } else {
                                    System.out.println(name + "热搜添加成功");
                                }
                                break;
                            }
                            case 3: {
                                System.out.println("请输入你要添加的超级热搜事件的名字：");
                                String name = readName();
                                HotSpot hotSpot = hotSpotService.add(name, Boolean.TRUE);
                                if (hotSpot == null) {
                                    System.out.println(name + "热搜已经存在");
                                } else {
                                    System.out.println(name + "超级热搜添加成功");
                                }
                                break;
                            }
                        }

                    }while (command != 4);
                    break;
                }
                default:
                    break;
            }
        }
    }

    private static String readName() {
        String name = sc.next();
        return name;
    }

    private static Integer readCommand() {
        // TODO: 2020/9/7
        int isuser = sc.nextInt();
        return isuser;
    }


    public static boolean isManager(String managerName, String password) {
        if(User.managerName.equals(managerName) && User.password.equals(password)) return true;
        return false;
    }

    //查看热搜排行榜
    public static void getRankedHotSpot() {
        HotSpotRepository hotSpotRepository =HotSpotRepository.getHotSpotRepository();
        List<HotSpot> hotSpots = hotSpotRepository.findAll();
        List<HotSpot> Hot_Spots = sort(hotSpots);

        Integer i = 0;
        for (HotSpot hotSpot : Hot_Spots) {
            i +=1;
            hotSpot.setRank(i);
            System.out.println(String.format("%d  %s %d",hotSpot.getRank(),hotSpot.getName(),hotSpot.getHot()));
        }
    }

    public static List<HotSpot> sort(List<HotSpot> list){
        Collections.sort(list,
                new Comparator<HotSpot>() {
                    @Override
                    public int compare(HotSpot o1, HotSpot o2) {
                        if (o1.getCurrentPrice() == 0 && o2.getCurrentPrice() == 0)
                            return new Integer(o2.getHot()).compareTo(o1.getHot());
                        else return new Integer(o1.getRank()).compareTo(o2.getRank());
                    }
                });
        return  list;
    }

    //热搜投票
    public static void addHotSpot(User user){
        System.out.println("请输入你要投票的热搜名称:");
        String name = readName();
        HotSpot hotSpot = hotSpotService.isadd(name);
        if(hotSpot != null) {
            System.out.println("请输入你要投票的热搜票数:"+"(剩余票数"+user.getVoteChance()+"票)");
            Integer voteNumer = readCommand();
            if(voteNumer > user.getVoteChance()){
                System.out.println("票数不够，投票失败");
            }else {
                HotSpot addHot = hotSpotService.addHot(name, user, voteNumer);
                System.out.println("投票成功");
            }
        }else {
            System.out.println("该热搜不存在，投票失败");
        }

    }
    public static void hotSpotPay(){
        HotSpotRepository hotSpotRepository =HotSpotRepository.getHotSpotRepository();
        List<HotSpot> hotSpots = hotSpotRepository.findAll();

        System.out.println("请输入你要购买的热搜名称:");
        String name = readName();
        HotSpot hotSpot = hotSpotService.isadd(name);
        if(hotSpot != null) {
            System.out.println("请输入你要购买的热搜排名:");
            Integer payRank = readCommand();
            HotSpot hotSpotRank = hotSpotRepository.findRank(payRank);
            if(payRank < 1 || !(payRank <= hotSpots.size())){
                System.out.println("不在购买排名范围内,购买失败");
            }else {
                System.out.println("请输入你要购买的热搜金额:");
                Integer pay = readCommand();

                if (pay <= hotSpots.get(payRank-1).getCurrentPrice() || pay < 1) System.out.println("购买金额较小，购买失败");
                else {
                    if(hotSpotRank.getCurrentPrice() > 0){
                        hotSpot.setRank(payRank);
                        hotSpot.setCurrentPrice(pay);

                        if(hotSpot.getName() != hotSpotRank.getName()) hotSpots.remove(hotSpotRank);
                        System.out.println("购买成功");

                    }else{
                        if(hotSpot.getRank() < payRank) {
                            for (int i = 1; i < hotSpots.size(); i++) {
                                if (i < hotSpot.getRank()) {
                                    hotSpots.get(i - 1).setRank(i);
                                } else if (i > hotSpot.getRank() && i <= payRank) {
                                    hotSpots.get(i - 1).setRank(i - 1);
                                } else {
                                    hotSpots.get(i - 1).setRank(i);
                                }

                            }
                        }else {
                            for (int i = 1; i < hotSpots.size(); i++) {
                                if (i < payRank) {
                                    hotSpots.get(i - 1).setRank(i);
                                }else if (i < hotSpot.getRank() && i >= payRank) {
                                    hotSpots.get(i - 1).setRank(i + 1);
                                } else {
                                    if(hotSpots.get(i - 1).getName() != name)
                                        hotSpots.get(i - 1).setRank(i);
                                }

                            }

                        }

                        hotSpot.setRank(payRank);
                        hotSpot.setCurrentPrice(pay);

                        System.out.println("购买成功");

                    }
                }
                getRankedHotSpot();
            }

        }else{
            System.out.println("该热搜不存在，购买失败");
        }
    }


    private static void init() {
        hotSpotService = HotSpotService.getHotSpotService();
    }
}
