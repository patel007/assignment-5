/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginPage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author c0664341
 */
@ManagedBean
@SessionScoped
public class Login {
    private String username, password;
    private boolean logging;
    private User currentUser;

    public Login() {
        username = null;
        password = null;
        logging = false;
        currentUser = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String Login() 
    {
        String passhash = DBUtils.hash(password);
       for (User u : Users.getInstance().getUsers()) {
            if (username.equals(u.getUsername())
                    && passhash.equals(u.getPasshash())) {
                logging = true;
                currentUser = u;
                return "index";
            }
        }        
        currentUser = null;
        logging = false;
        return "index";
    }

    
}
