package pgtest.security.domain;

import java.util.List;

/**
 * Created by moshstef on 6/10/14.
 */
public class Role {
    private String role;
    private List<Authority> authorities;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
