package lab7.common.collection;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable {
    public String login;
    public String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int compareTo(User o){
        int result = login.compareTo(o.getLogin());
        if(result == 0){
            result = password.compareTo(o.getPassword());
        }
        return result;
    }
}
