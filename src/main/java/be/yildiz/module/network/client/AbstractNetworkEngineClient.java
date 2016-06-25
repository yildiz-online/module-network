//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.module.network.client;

import be.yildiz.common.collections.Lists;
import be.yildiz.common.log.Logger;
import be.yildiz.module.network.AuthenticationConfiguration;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.ServerRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The network engine can connect to a server, send messages, receive messages, delay message handling, notify listeners when an event occurs.
 *
 * @author Grégory Van den Borre
 */
@NoArgsConstructor
public abstract class AbstractNetworkEngineClient implements ClientCallBack {

    /**
     * List containing all messages received from the server, waiting to be processed.
     */
    private final List<MessageWrapper> messageReceivedList = Lists.newList();

    /**
     * List of message waiting to be used in the next call.
     */
    private final List<MessageWrapper> delayedMessage = Lists.newList();

    /**
     * List of all listeners receiving notification from the network.
     */
    private final List<NetworkListener> networkListenerList = Lists.newList();

    /**
     * <code>true</code> if a network connection is made with the server, <code>false</code> otherwise.
     */
    @Getter
    private boolean connected;

    /**
     * Parse the server messages and call appropriate logic.
     */
    public final void update() {
        this.messageReceivedList.addAll(this.delayedMessage);
        this.delayedMessage.clear();
        while (!this.messageReceivedList.isEmpty()) {
            final MessageWrapper message = this.messageReceivedList.remove(0);
            for (final NetworkListener listener : this.networkListenerList) {
                try {
                    listener.parse(message);
                } catch (InvalidNetworkMessage e) {
                    Logger.error(e);
                }
            }
        }
    }

    /**
     * Add a listener for a network message.
     *
     * @param listener Listener to add.
     */
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
    public abstract void sendMessage(ServerRequest message);

    /**
     * Send a message to the server.
     *
     * @param message Message to send.
     */
    public abstract void sendMessage(String message);

    /**
     * Try to connect to the server.
     *
     * @param address Server address.
     * @param port    Network port to use.
     */
    public abstract void connect(String address, int port);

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
        Logger.info("Connected to server.");
        this.networkListenerList.forEach(NetworkListener::connected);
    }

    @Override
    public final void connectionFailed() {
        this.connected = false;
        Logger.info("Cannot connect to server.");
        this.networkListenerList.forEach(NetworkListener::connectionFailed);
    }

    @Override
    public final void connectionLost() {
        if (this.connected) {
            this.connected = false;
            Logger.info("Connection lost to server.");
            this.networkListenerList.forEach(NetworkListener::connectionLost);
        }
    }

    /**
     * Disconnect the client without closing it.
     */
    public abstract void disconnect();

    public void connect(final AuthenticationConfiguration config) {
        this.connect(config.getAuthenticationHost(), config.getAuthenticationPort());
    }
}
