package com.daniela;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniela on 7/8/2016.
 */
public class UserDao {
    private String userFilePath;

    public UserDao() {
    }

    public UserDao(String userFilePath) {
        this.userFilePath = userFilePath;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            File userFile = getUsersFile();
            if (!userFile.exists()) {
                User user = new User(1, "Daniela", "programmer");
                userList.add(user);
                saveUserList(userList);
            } else {
                FileInputStream fis = new FileInputStream(userFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userList = (List<User>) ois.readObject();
                fis.close();
                fis=null;
                ois.close();
                ois=null;
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public User getUserById(int id) {
        for (User user : getAllUsers()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public boolean addUser(String name, String profession) {
        List<User> allUsers = getAllUsers();
        User user = new User(-1, name, profession);
        if (allUsers.contains(user)) {
            return false;
        }
        int newUserId = allUsers.get(allUsers.size() - 1).getId() + 1;
        user.setId(newUserId);
        allUsers.add(user);
        saveUserList(allUsers);
        return true;
    }

    public boolean modifyUser(int id, String profession) {
        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            if (user.getId() == id) {
                user.setProfession(profession);
                saveUserList(allUsers);
                return true;
            }
        }

        return false;
    }

    public boolean removeUser(int id) {
        List<User> allUsers = getAllUsers();
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getId() == id) {
                allUsers.remove(i);
                saveUserList(allUsers);
                return true;
            }
        }
        return false;
    }

    private File getUsersFile() {
        String path = this.userFilePath != null ? this.userFilePath : "Users.dat" ;
        return new File(path);
    }

    private void saveUserList(List<User> userList) {
        try {
            File file = getUsersFile();
            FileOutputStream fos = new FileOutputStream(file);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userList);
            oos.flush();
            oos.close();
            oos = null;
            fos.flush();
            fos.close();
            fos= null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
