package pgtest.security.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by moshstef on 6/10/14.
 */
public class Authority implements GrantedAuthority {
    private Long id;
    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
