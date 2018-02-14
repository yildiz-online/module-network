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

package be.yildizgames.module.network.server;

import be.yildizgames.common.logging.LogFactory;
import be.yildizgames.common.model.PlayerId;
import be.yildizgames.module.network.protocol.NetworkMessage;

import java.util.Set;

/**
 * A session is a player when it is currently playing.
 *
 * @author Grégory Van den Borre
 */
public abstract class Session {

    private static final org.slf4j.Logger LOGGER = LogFactory.getInstance().getLogger(Session.class);

    /**
     * Associated player.
     */
    private PlayerId player;

    /**
     * <code>true</code> if the client is physically connected to the server.
     */
    private boolean connected;

    /**
     * <code>true</code> if the client's credentials have been accepted.
     */
    private boolean authenticated;

    /**
     * Full constructor.
     *
     * @param player Player associated to this session.
     */
    protected Session(final PlayerId player) {
        super();
        assert player != null;
        this.player = player;
        this.connected = true;
    }

    /**
     * Called when the client is disconnected.
     */
    public final void disconnect() {
        LOGGER.info(this.getPlayer() + " disconnected.");
        this.connected = false;
        this.authenticated = false;
        this.closeSession();
    }

    /**
     * Set this session as setAuthenticated.
     */
    public final void setAuthenticated() {
        this.authenticated = true;
    }

    /**
     * Send a message to the connected client.
     *
     * @param message Message to send to the client.
     */
    public final void sendMessage(final NetworkMessage message) {
        assert message != null;
        this.sendMessage(message.buildMessage());
    }

    /**
     * Send a list of messages to the connected client.
     *
     * @param messageList List of messages to send to the client.
     */
    public final void sendMessage(final Set<NetworkMessage> messageList) {
        assert messageList != null;
        messageList.forEach(this::sendMessage);
    }

    /**
     * Close the session if the client has been disconnected.
     */
    protected abstract void closeSession();


    public abstract void sendMessage(String message);

    /**
     * @return <code>true</code>.
     */
    public final boolean hasPlayer() {
        return true;
    }

    public final PlayerId getPlayer() {
        return player;
    }

    public final boolean isConnected() {
        return connected;
    }

    public final boolean isAuthenticated() {
        return authenticated;
    }

    public final void setPlayer(PlayerId player) {
        assert player != null;
        this.player = player;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.player == null) ? 0 : this.player.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Session)) {
            return false;
        }
        Session other = (Session) obj;
        return this.player.equals(other.player);
    }
}
