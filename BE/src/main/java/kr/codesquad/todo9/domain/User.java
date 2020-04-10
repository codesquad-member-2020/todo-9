package kr.codesquad.todo9.domain;

import java.util.Map;

public class User {
    private Long id;
    private String username;

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(Map<String, Object> data) {
        this.id = Long.parseLong(String.valueOf(data.get("id")));
        this.username = (String) data.get("username");
    }
  
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + '}';
    }
}
