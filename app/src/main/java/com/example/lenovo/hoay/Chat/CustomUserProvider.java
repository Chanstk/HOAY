package com.example.lenovo.hoay.Chat;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * Created by chanst on 2017/3/26.
 */

public class CustomUserProvider implements LCChatProfileProvider {
    private static CustomUserProvider customUserProvider;

    public synchronized static CustomUserProvider getInstance() {
        if (null == customUserProvider) {
            customUserProvider = new CustomUserProvider();
        }
        return customUserProvider;
    }

    private CustomUserProvider() {
    }

    private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

    // 此数据均为模拟数据，仅供参考
    static {
        partUsers.add(new LCChatKitUser("17826850449", "邵雅琪", "http://www.avatarsdb.com/avatars/jerry.jpg"));
        partUsers.add(new LCChatKitUser("15063998370", "陈石涛", "http://www.avatarsdb.com/avatars/young_harry.jpg"));
        partUsers.add(new LCChatKitUser("17826850452", "郑千千", "http://www.avatarsdb.com/avatars/bath_bob.jpg"));
        partUsers.add(new LCChatKitUser("18968885981", "周昱莹", "http://www.avatarsdb.com/avatars/bath_bob.jpg"));
    }

    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {

        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
        for (String userId : list) {
            for (LCChatKitUser user : partUsers) {
                if (user.getUserId().equals(userId)) {
                    userList.add(user);
                    break;
                }
            }
        }
        callBack.done(userList, null);
    }

    public List<LCChatKitUser> getAllUsers() {
        return partUsers;
    }

}
