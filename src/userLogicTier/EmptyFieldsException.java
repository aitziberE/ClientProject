/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

/**
 *
 * @author Usuario
 */
public class EmptyFieldsException extends Exception{

    public EmptyFieldsException() {
    }
    
    public EmptyFieldsException(String msg) {
        super(msg);
    }
    
}
