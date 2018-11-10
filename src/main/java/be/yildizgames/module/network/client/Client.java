/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.module.network.client;

import be.yildizgames.common.logging.LogFactory;
import be.yildizgames.module.network.client.dummy.DummyClientProvider;
import be.yildizgames.module.network.exceptions.InvalidNetworkMessage;
import be.yildizgames.module.network.exceptions.NetworkException;
import be.yildizgames.module.network.protocol.MessageWrapper;
import be.yildizgames.module.network.protocol.NetworkMessage;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * The network engine can connect to a server, send messages, receive messages, delay message handling, notify listeners when an event occurs.
 * This class work asynchronously, its state is changed by the reception of events.
 * i.e: When calling disconnect, the connected value will not be set to false as long as a listener has not notified that the connection is lost.
 *
 * @author Grégory Van den Borre
 */
public abstract class Client implements NetworkClient, ClientCallBack {

    private static final Logger LOGGER = LogFactory.getInstance().getLogger(Client.class);

    /**
     * List containing all messages received from the server, waiting to be processed.
     */
    private final List<MessageWrapper> messageReceivedList = new ArrayList<>();

    /**
     * List of message waiting to be used in the next call.
     */
    private final List<MessageWrapper> delayedMessage = new ArrayList<>();

    /**
     * List of all listeners receiving notification from the network.
     */
    private final List<NetworkListener> networkListenerList = new ArrayList<>();

    /**
     * <code>true</code> if a network connection is made with the server, <code>false</code> otherwise.
     * This value is changed by the reception of event from the network listener, i.e calling disconnect will not set connected to false as long as the listener as not notified that the connection is lost.
     */
    private boolean connected;

    /**
     * Flag to check if the system is currently trying to connect, to avoid retrying while this is not complete
     */
    private boolean connecting;

    private final ConnectionRetryStrategy connectionRetryStrategy = ConnectionRetryStrategy.none();

    /**
     * Url of the host to try to connect.
     */
    private String address;

    /**
     * Port of the host to try to connect.
     */
    private int port;


    public Client() {
        super();
    }

    public static Client getEngine() {
        ServiceLoader<ClientProvider> provider = ServiceLoader.load(ClientProvider.class);
        return provider.findFirst().orElseGet(DummyClientProvider::new).getEngine();
    }

    /**
     * Parse the server messages and call appropriate logic.
     */
    public final void update() {
        this.messageReceivedList.addAll(this.delayedMessage);
        this.delayedMessage.clear();
        while (!this.messageReceivedList.isEmpty()) {
            final MessageWrapper message = this.messageReceivedList.remove(0);
            for(int i = 0; i < this.networkListenerList.size(); i++) {
                try {
                    networkListenerList.get(i).parse(message);
                } catch (InvalidNetworkMessage e) {
                    LOGGER.error("Invalid message", e);
                }
            }
        }
        if(!this.connected && !this.connecting && this.connectionRetryStrategy.canRetryToConnect()) {
            this.connectImpl(this.address, this.port);
        }
    }

    /**
     * Add a listener for a network message.
     *
     * @param listener Listener to add.
     */
    @Override
    public final void addNetworkListener(final NetworkListener listener) {
        this.networkListenerList.add(listener);
    }

    /**
     * Sometimes the application has not finished to build an entity when a message for that entity comes from the network. This method delay the message to the next frame until the entity exists.
     *
     * @param message Server message to delay.
     */
    public final void delayMessageToNextFrame(final MessageWrapper message) {
        this.delayedMessage.add(message);
    }

    /**
     * Send a message to the server.
     *
     * @param message Message to send.
     */
    @Override
    public abstract void sendMessage(NetworkMessage message);

    /**
     * Send a message to the server.
     *
     * @param message Message to send.
     */
    public abstract void sendMessage(String message);

    @Override
    public final void connect(String address, int port) {
        this.address = address;
        this.port = port;
        this.connecting = true;
        this.connectImpl(address, port);
    }

    public abstract void connectImpl(String address, int port);

    /**
     * Free resources used by the engine.
     */
    public abstract void close();

    @Override
    public final void messageReceived(final MessageWrapper message) {
        this.messageReceivedList.add(message);
    }

    /**
     * Called from child when connected to the server, it will notify the listeners.
     */
    protected final void connectionSuccessful() {
        this.connected = true;
        this.connecting = false;
        LOGGER.info("Client connected to server.");
        this.networkListenerList.forEach(NetworkListener::connected);
    }

    @Override
    public final void connectionFailed() {
        this.connected = false;
        this.connecting = false;
        LOGGER.warn("Cannot connect to server.");
        this.networkListenerList.forEach(NetworkListener::connectionFailed);
    }

    @Override
    public final void connectionLost() {
        this.connecting = false;
        if (this.connected) {
            this.connected = false;
            LOGGER.warn("Connection lost to server.");
            this.networkListenerList.forEach(NetworkListener::connectionLost);
        }
    }

    protected final void throwError(String message) {
        throw new NetworkException(message);
    }

    /**
     * Disconnect the client without closing it.
     */
    public abstract void disconnect();

    public boolean isConnected() {
        return connected;
    }
}
