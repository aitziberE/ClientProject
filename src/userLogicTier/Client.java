package userLogicTier;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import userLogicTier.model.User;
import userLogicTier.Message;
import userLogicTier.MessageType;


/**
 * Clase que implementa la lógica del cliente encargada de enviar mensajes al servidor utilizando User y MessageType.
 */
public class Client implements Signable {

    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private final int PUERTO = 5000;
    private final String IP = "127.0.0.1";

    // Método para enviar el mensaje al servidor y recibir la respuesta
    private Message enviarMensajeAlServidor(Message mensaje) {
        Socket socket = null;
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        Message respuesta = null;

        logger.log(Level.INFO, "Initializing Client...");

        try {
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
    @Override
    public User signUp(User user) {
        // Crear el mensaje de tipo SERVER_SIGN_UP_REQUEST con el User
        Message mensaje = new Message(user, MessageType.SERVER_SIGN_UP_REQUEST);

        // Enviar el mensaje al servidor
        Message respuesta = enviarMensajeAlServidor(mensaje);

        // Procesar la respuesta del servidor
        if (respuesta != null) {
            switch (respuesta.getMessageType()) {
                case SERVER_RESPONSE_OK:
                     logger.log(Level.INFO, "User signed up.");
                    return respuesta.getUser(); // Registro exitoso, retorna el usuario
                case SERVER_USER_ALREADY_EXISTS:
                    logger.log(Level.INFO, "User already exists.");
//throws new exception User                    
                case SERVER_USER_CAP_REACHED:
                    logger.log(Level.INFO, "User limit reached on server.");
                    return null;
                case SERVER_RESPONSE_DENIED:
                    logger.log(Level.INFO, "Registration denied by server.");
                    return null;
                case SERVER_CONNECTION_ERROR:
                    logger.log(Level.INFO, "Connection error with the server.");
                    return null;
                default:
                    logger.log(Level.WARNING, "Unexpected server response.");
                    return null;
            }
        } else {
            logger.log(Level.SEVERE, "No response from server.");
            return null;
        }
    }

    // Implementación del método signIn
    @Override
    public User signIn(User user) {
        // Crear el mensaje de tipo SERVER_SIGN_IN_REQUEST con el User
        Message mensaje = new Message(user, MessageType.SERVER_SIGN_IN_REQUEST);

        // Enviar el mensaje al servidor y recibir la respuesta
        Message respuesta = enviarMensajeAlServidor(mensaje);

        // Procesar la respuesta del servidor
        if (respuesta != null) {
            switch (respuesta.getMessageType()) {
                case SERVER_RESPONSE_OK:
                    return respuesta.getUser(); // Inicio de sesión exitoso, retorna el usuario
                case SERVER_USER_NOT_FOUND:
                    logger.log(Level.INFO, "User not found.");
                    return null;
                case SERVER_RESPONSE_DENIED:
                    logger.log(Level.INFO, "Login denied by server.");
                    return null;
                case SERVER_CONNECTION_ERROR:
                    logger.log(Level.INFO, "Connection error with the server.");
                    return null;
                default:
                    logger.log(Level.WARNING, "Unexpected server response.");
                    return null;
            }
        } else {
            logger.log(Level.SEVERE, "No response from server.");
            return null;
        }
    }
}
