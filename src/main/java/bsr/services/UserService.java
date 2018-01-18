package bsr.services;

import bsr.exception.AccountException;
import bsr.model.User;

public interface UserService {
    User getUser(String login, String password) throws AccountException;
    User getUser(String token) throws AccountException;
}
