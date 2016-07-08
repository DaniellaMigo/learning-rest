package com.daniela;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniela on 7/8/2016.
 */
public class UserDaoTest {

    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        userDao = new UserDao("tmp.dat");
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> actual = new ArrayList<>();
        actual.add(new User(1, "Daniela", "programmer"));

        List<User> allUsers = userDao.getAllUsers();

        Assert.assertEquals(allUsers, actual);
    }

    @Test
    public void testGetUserById() throws Exception {
        User actual = new User(1, "Daniela", "programmer");

        User expected = userDao.getUserById(1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAddUser() throws Exception {
        userDao.addUser("Vasile", "gunoier");

        Assert.assertEquals(2, userDao.getAllUsers().size());
    }

    @Test
    public void testWhenAddingUserIdIsIncreasedAutomatically() throws Exception {
        List<User> allUsers = userDao.getAllUsers();
        Assert.assertEquals(1, allUsers.get(allUsers.size() - 1).getId());
        Assert.assertEquals(1, userDao.getAllUsers().size());

        userDao.addUser("Marinela", "cantareata");
        allUsers = userDao.getAllUsers();

        Assert.assertEquals(2, allUsers.get(allUsers.size() - 1).getId());
        Assert.assertEquals(2, userDao.getAllUsers().size());
    }

    @Test
    public void testRemoveUser() throws Exception {
        List<User> allUsers = userDao.getAllUsers();


    }

    @After
    public void tearDown() throws Exception {
        File file = new File("tmp.dat");
        if(file.delete()){
            System.out.println(file.getName() + " is deleted!");
        }else{
            System.out.println("Delete operation failed.");
        }

    }
}

class UserFileProvider {
    public static File getUsersFile() {
        File file = new File("tmp.dat");
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "Daniela", "programmer"));
        try {
            FileOutputStream fos = new FileOutputStream(file);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
