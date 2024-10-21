/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import userLogicTier.model.User;

/**
 *
 * @author Ander
 */
public class Client implements Signable {

    @Override
    public void signUp(User user) {

    }

    @Override
    public User signIn(User user) {

        return user;
    }

}
