package userLogicTier;

import exceptions.ExistingUserException;
import exceptions.InactiveUserException;
import exceptions.ServerException;
import exceptions.UserCapException;
import exceptions.UserCredentialException;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;
import message.Message;
import message.MessageType;
import userLogicTier.model.User;

/**
 * The {@code Client} class implements the client logic for communicating with a server using {@link User} and {@link MessageType} objects. It manages the sending and receiving of {@link Message} objects and handles various server responses based on message types.
 * <p>
 * This class uses {@link Signable} to provide sign-up and sign-in operations, processing server responses and managing exceptions specific to user registration and authentication.
 * </p>
 * <p>
 * <b>Configuration:</b> The server IP and port are loaded from a configuration file.
 * </p>
 *
 * <p>
 * <b>Usage:</b></p>
 * <pre>
 *     Client client = new Client();
 *     client.loadConfig();
 *     client.signUp(user);
 * </pre>
 *
 * @see Signable
 * @see Message
 * @see MessageType
 * @see User
 * @see ExistingUserException
 * @see InactiveUserException
 * @see ServerException
 * @see UserCapException
 * @see UserCredentialException
 *
 * @author Pebble
 * @version 1.0
 */
public class Client implements Signable {

    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private int PORT;
    private String IP;

    /**
     * Sends a {@link Message} object to the server and receives the server's response. Initializes the connection, sends the message, and waits for the response.
     *
     * @param message the {@link Message} to be sent to the server
     * @return the {@link Message} received from the server, or {@code null} if no response is received
     */
    private Message handleMessage(Message message) {
        Socket socket = null;
        ObjectInputStream reader = null;
        ObjectOutputStream writer = null;
        Message responseMessage = null;

        logger.log(Level.INFO, "Initializing Client...");

        try {
            loadConfig();
            logger.log(Level.INFO, "Configuration loaded");
            // Establish connection with the server
            socket = new Socket(IP, PORT);
            logger.log(Level.INFO, "Socket obtained");
            // Initialize output and input streams
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());
            logger.log(INFO, "Writer & reader instanced");

            // Send the message to the server
            logger.log(INFO, "Writing message");
            writer.writeObject(message);

            // Receive the response from the server
            logger.log(Level.INFO, "Receiving message");
            responseMessage = (Message) reader.readObject();

        } catch (EOFException e) {
            logger.log(Level.SEVERE, "EOFException: No response from server, possible connection closure.", e);
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with server: ", e);
        } finally {

            // Close socket and streams
            try {
                if (socket != null) {
                    socket.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error closing resources: ", e.getMessage());
            }
        }
        return responseMessage; // Return the server response
    }

    /**
     * Registers a new user with the server.
     *
     * @param user the {@link User} to register
     * @return the registered {@link User} object if registration is successful
     * @throws ExistingUserException if the user already exists on the server
     * @throws ServerException if a server-related error occurs during registration
     */
    @Override
    public User signUp(User user) throws ExistingUserException, ServerException {
        User responseUser = null;

        logger.log(Level.SEVERE, user.getPassword());

        // Create the registration message for the server
        Message request = new Message(user, MessageType.SERVER_SIGN_UP_REQUEST);

        // Send the message and receive the response
        Message response = handleMessage(request);

        // Process the server's response
        if (response != null) {
            switch (response.getMessageType()) {
                case SERVER_RESPONSE_OK:
                    responseUser = response.getUser();
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

        // Successful registration, return the user
        return responseUser;
    }

    /**
     * Authenticates an existing user with the server.
     *
     * @param user the {@link User} attempting to sign in
     * @return the authenticated {@link User} object if login is successful
     * @throws InactiveUserException if the user is inactive
     * @throws UserCredentialException if the user credentials are invalid
     * @throws UserCapException if the server has reached the maximum number of users
     * @throws ServerException if a server-related error occurs during authentication
     */
    @Override
    public User signIn(User user) throws InactiveUserException, UserCredentialException, UserCapException, ServerException {
        User responseUser = null;

        // Create the sign-in message for the server
        Message message = new Message(user, MessageType.SERVER_SIGN_IN_REQUEST);
        // Send the message and receive the response
        Message response = handleMessage(message);

        // Process the server's response
        if (response != null) {
            switch (response.getMessageType()) {
                case SERVER_RESPONSE_OK:
                    responseUser = response.getUser();
                    logger.log(Level.INFO, "User signed in.");
                    break;
                case SERVER_USER_INACTIVE:
                    logger.log(Level.SEVERE, "Inactive user.");
                    throw new InactiveUserException();
                case SERVER_USER_CREDENTIAL_ERROR:
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

        // Successful login, return the user
        return responseUser;
    }

    /**
     * Loads configuration data (such as IP address and port) from a properties file.
     */
    public void loadConfig() {
        ResourceBundle configFile = ResourceBundle.getBundle("resources.config");
        IP = configFile.getString("IP");
        PORT = Integer.parseInt(configFile.getString("PORT"));
    }
}
