
package opintopaivakirjasovellus.domain;

public class User {
    private String name;
    private String username;
    
    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }
    public String getName() {
        return this.name;
    }
    public String getUsername() {
        return this.username;
    }
}
