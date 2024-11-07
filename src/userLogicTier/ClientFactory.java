/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

/**
 * A factory class for creating instances of classes that implement the {@link Signable} interface.
 *
 * This class provides a static method to retrieve an instance of {@link Client}, which implements
 * the {@link Signable} interface. This approach centralizes the creation of {@link Signable} objects,
 * promoting a single point of modification if the implementation changes.
 * 
 * Usage Example:
 *     Signable client = ClientFactory.getSignable();
 * 
 * @see Signable
 * @see Client
 * 
 * @author Ander
 */
public class ClientFactory {
    
    /**
     * Returns an instance of a class that implements {@link Signable}.
     * 
     * Currently, this method returns an instance of {@link Client}. This may be updated to return
     * different implementations of {@link Signable} as required.
     * 
     * @return a {@link Signable} instance, specifically a {@link Client} instance
     */
    public static Signable getSignable(){
        
        return new Client();
    }
}
