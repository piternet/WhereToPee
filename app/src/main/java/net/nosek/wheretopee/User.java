package net.nosek.wheretopee;


public class User {
    private int id;
    private String nickname;
    private String phoneInfo;

    public User(int id, String nickname, String phoneInfo) {
        this.id = id;
        this.nickname = nickname;
        this.phoneInfo = phoneInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(String phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", phoneInfo='" + phoneInfo + '\'' +
                '}';
    }
}
