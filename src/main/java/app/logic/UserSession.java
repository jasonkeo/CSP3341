
package app.logic;

public class UserSession {
    public String username;
    public String role;

    public UserSession(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public boolean isAdmin() {
        return "admin".equals(role);
    }
}