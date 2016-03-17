/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginPage;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author c0664341
 */
@ManagedBean
@ApplicationScoped
public class Users {
    static Object getInstance;
    
    private List<User> users;
    private static Users instance;
    
    public Users()
    {
        getUsersFromDB();
        instance = this;
    }
    
    private void getUsersFromDB()
    {
        try (Connection conn = DBUtils.getConnection()) {
            users = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("passhash")
                );
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);          
            users = new ArrayList<>();
        }
                               
    }
    
    public List<User> getUsers()
    {
        return users;
    }
    
    public static Users getInstance()
    {
        return instance;
    }
    
    public String getUsernameById(int id)
    {
        for(User us : users)
        {
            if(us.getId() == id)
            {
                return us.getUsername();
            }
        }
        return null;
    }

    public int getUserIdByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.getId();
            }
        }
        return -1;
    }
    
        public void addUser(String username, String password) 
        {
        try (Connection conn = DBUtils.getConnection()) 
        {
            String passhash = DBUtils.hash(password);
            String sql = "INSERT INTO users (username, passhash) VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, passhash);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        getUsersFromDB();
    }

  
}
