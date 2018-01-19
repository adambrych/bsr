package bsr.services;

import bsr.exception.BankException;
import bsr.model.User;

public interface UserService {
    /**
     * get User using login and password
     * @param login
     * @param password
     * @return
     * @throws BankException
     */
    User getUser(String login, String password) throws BankException;

    /**
     * get User by checking token
     * @param token
     * @return
     * @throws BankException
     */
    User getUser(String token) throws BankException;
}
