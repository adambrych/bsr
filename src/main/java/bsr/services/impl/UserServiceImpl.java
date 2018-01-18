package bsr.services.impl;

import bsr.dao.UserDao;
import bsr.exception.AccountException;
import bsr.exception.ServiceFault;
import bsr.model.User;
import bsr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao userDao;

    @Override
    public User getUser(String login, String password) throws AccountException {
        User user = userDao.findByLoginAndPassword(login, password);
        if(user == null)
            throw new AccountException("ERROR", new ServiceFault("Not found", "User with this credentials doesn't exists"));
        return user;
    }

    @Override
    public User getUser(String token) throws AccountException {
        User user = userDao.findByLogin(token);
        if(user == null)
            throw new AccountException("ERROR", new ServiceFault("Not found", "User with this token doesn't exists"));
        return user;
    }
}
