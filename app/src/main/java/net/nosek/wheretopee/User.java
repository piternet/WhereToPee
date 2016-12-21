package net.nosek.wheretopee;


public class User {
    private long id;
    private String nickname;
    private String phoneInfo;

    public User(long id, String nickname, String phoneInfo) {
        this.id = id;
        this.nickname = nickname;
        this.phoneInfo = phoneInfo;
    }

    public long getId() {
        return id;
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
