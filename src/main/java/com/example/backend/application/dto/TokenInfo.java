package rs.raf.demo.dto;

import rs.raf.demo.models.User;

public class TokenInfo {
    
    private Integer id;
    private String email;
    private String type;
    private boolean active;

    public TokenInfo() {
    }

    public TokenInfo(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.type = user.getType();
        this.active = user.isActive();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
