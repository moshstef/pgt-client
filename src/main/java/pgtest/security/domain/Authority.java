package pgtest.security.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by moshstef on 6/10/14.
 */
public class Authority implements GrantedAuthority {
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
