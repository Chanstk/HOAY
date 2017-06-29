package com.example.lenovo.hoay.bean;

/**
 * Created by chanst on 2017/4/25.
 */

public class DestinyBean {
        public String id, photo, nickname;
        public int destiny, undestiny;
        public DestinyBean(String nickname, String photo, String id, int destiny, int undestiny) {
            this.id = id;
            this.nickname = nickname;
            this.photo = photo;
            this.destiny = destiny;
            this.undestiny = undestiny;
        }

}
