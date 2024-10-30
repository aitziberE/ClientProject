package userLogicTier;

import message.MessageType;
import message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import userLogicTier.model.User;

/**
 * Clase que implementa la lógica del cliente encargada de enviar mensajes al servidor utilizando User y MessageType.
 */
public class Client implements Signable {

    private static final Logger logger = Logger.getLogger(HomeController.class.getName());
    // CREAR EL ARCHIVO PROPERTIES
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

            }
        }

        return respuesta; // Retornar la respuesta del servidor
    }

    // Implementación del método signUp
    @Override
    public void signUp(User user) {
        // Crear el mensaje de tipo SERVER_REQUEST con el User
        Message mensaje = new Message(user, MessageType.SERVER_SIGN_UP_REQUEST);

        // Enviar el mensaje al servidor
        enviarMensajeAlServidor(mensaje);
    }

    // Implementación del método signIn
    @Override
    public User signIn(User user) {

        // Crear el mensaje de tipo SERVER_REQUEST con el User
        Message mensaje = new Message(user, MessageType.SERVER_SIGN_IN_REQUEST);

        // Enviar el mensaje al servidor y recibir la respuesta
        Message respuesta = enviarMensajeAlServidor(mensaje);

        // Si la respuesta es positiva, retornar el usuario, de lo contrario, retornar null
        if (respuesta != null && respuesta.getMessageType() == MessageType.SERVER_RESPONSE_OK) {
            return respuesta.getUser();
        } else {
            return null; // Retorna null si no es exitoso
            //CREAR UN MESSAGETYPE DEL SERVIDOR Y METER UN IF/ SWITCH DEPENDIENDO DE LA RESPUESTA DEL SERVIDOR
        }
    }
}
