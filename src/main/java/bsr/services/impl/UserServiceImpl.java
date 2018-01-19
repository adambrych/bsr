package bsr.services.impl;

import bsr.dao.UserDao;
import bsr.exception.BankException;
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
    public User getUser(String login, String password) throws BankException {
        User user = userDao.findByLoginAndPassword(login, password);
        if(user == null)
            throw new BankException("ERROR", new ServiceFault("Not found", "User with this credentials doesn't exists"));
        return user;
    }

    @Override
    public User getUser(String token) throws BankException {
        User user = userDao.findByLogin(token);
        if(user == null)
            throw new BankException("ERROR", new ServiceFault("Not found", "User with this token doesn't exists"));
        return user;
    }
}
