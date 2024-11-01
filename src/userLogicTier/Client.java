package userLogicTier;

import exceptions.ExistingUserException;
import exceptions.InactiveUserException;
import exceptions.ServerException;
import exceptions.UserCapException;
import exceptions.UserCredentialException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;
import message.MessageType;
import userLogicTier.model.User;

/**
 *
 * @author Ander
 * @author Pablo
 * @author Aitziber
 * 
 * Clase que implementa la lógica del cliente encargada de enviar mensajes al servidor utilizando User y MessageType.
 */
public class Client implements Signable {

    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private int PUERTO;
    private String IP;

    // Método para enviar el mensaje al servidor y recibir la respuesta
    private Message enviarMensajeAlServidor(Message mensaje) {
        Socket socket = null;
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        Message respuesta = null;

        logger.log(Level.INFO, "Initializing Client...");

        try {
            
            loadConfig();
           
            // Establecer conexión con el servidor
            socket = new Socket(IP, PUERTO);

            // Inicializar los flujos de salida y entrada
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            // Enviar el mensaje al servidor
            salida.writeObject(mensaje);

            // Recibir la respuesta del servidor
            respuesta = (Message) entrada.readObject();

        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with server: {0}", e.getMessage());
        } finally {
            // Cerrar los recursos de socket y streams
            try {
                if (socket != null) {
                    socket.close();
                }
                if (salida != null) {
                    salida.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error closing resources: {0}", e.getMessage());
            }
        }

        return respuesta; // Retornar la respuesta del servidor
    }

    // Implementación del método signUp

    /**
     *
     * @param user
     * @return
     * @throws ExistingUserException
     * @throws ServerException
     */
    @Override
    public User signUp(User user) throws ExistingUserException, ServerException {
        User responseUser = null;
        
        // Crear el mensaje de tipo SERVER_SIGN_UP_REQUEST con el User
        Message peticion = new Message(user, MessageType.SERVER_SIGN_UP_REQUEST);

        // Enviar el mensaje al servidor
        Message respuesta = enviarMensajeAlServidor(peticion);

        // Procesar la respuesta del servidor
        if (respuesta != null) {
            switch (respuesta.getMessageType()) {
                case SERVER_RESPONSE_OK:
                    responseUser = respuesta.getUser();
                    logger.log(Level.INFO, "User signed up.");
                    break;
                case SERVER_USER_ALREADY_EXISTS:
                    logger.log(Level.SEVERE, "User already exists.");
                    throw new ExistingUserException();
                case SERVER_RESPONSE_DENIED:
                    logger.log(Level.SEVERE, "Registration denied by server.");
                    throw new ServerException();
                case SERVER_CONNECTION_ERROR:
                    logger.log(Level.SEVERE, "Connection error with the server.");
                    throw new ServerException();
                default:
                    logger.log(Level.SEVERE, "Unexpected server response.");
                    throw new ServerException();
            }         

        } else {
            logger.log(Level.SEVERE, "No response from server.");
            throw new ServerException();
        }
        
        // Registro exitoso, retorna el usuario
        return responseUser;
    }

    // Implementación del método signIn

    /**
     *
     * @param user
     * @return
     * @throws InactiveUserException
     * @throws UserCredentialException
     * @throws UserCapException
     * @throws ServerException
     */
    @Override
    public User signIn(User user) throws InactiveUserException, UserCredentialException, UserCapException, ServerException {
        User responseUser = null;

        // Crear el mensaje de tipo SERVER_SIGN_IN_REQUEST con el User
        Message mensaje = new Message(user, MessageType.SERVER_SIGN_IN_REQUEST);

        // Enviar el mensaje al servidor y recibir la respuesta
        Message respuesta = enviarMensajeAlServidor(mensaje);

        // Procesar la respuesta del servidor
        if (respuesta != null) {
            switch (respuesta.getMessageType()) {
                case SERVER_RESPONSE_OK:
                    responseUser = respuesta.getUser();
                    logger.log(Level.INFO, "User signed in.");
                    break;
                case SERVER_USER_INACTIVE:
                    logger.log(Level.SEVERE, "Inactive user.");
                    throw new InactiveUserException();
                case SERVER_USER_NOT_FOUND:
                    logger.log(Level.SEVERE, "User not found.");
                    throw new UserCredentialException();
                case SERVER_USER_CAP_REACHED:
                    logger.log(Level.SEVERE, "User limit reached on server.");
                    throw new UserCapException();
                case SERVER_RESPONSE_DENIED:
                    logger.log(Level.SEVERE, "Login denied by server.");
                    throw new ServerException();
                case SERVER_CONNECTION_ERROR:
                    logger.log(Level.SEVERE, "Connection error with the server.");
                    throw new ServerException();
                default:
                    logger.log(Level.SEVERE, "Unexpected server response.");
                    throw new ServerException();
            }
        } else {
            logger.log(Level.SEVERE, "No response from server.");
            throw new ServerException();
        }
        
        // Inicio de sesión exitoso, retorna el usuario
        return responseUser;
    }
    
    /**
     * Loads config data into class variables.
     */
    public void loadConfig() {
        ResourceBundle configFile = ResourceBundle.getBundle("config.properties");
        IP = configFile.getString("IP");
        PUERTO = Integer.parseInt(configFile.getString("PORT"));
    }
}
