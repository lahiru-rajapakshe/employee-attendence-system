package security;

public class Principal {
    private String username;
    private String name;
    private UserRole role;

    public Principal() {
    }

    public Principal(String username, String name, UserRole role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }

    public enum UserRole {
        ADMIN, USER
    }
}
