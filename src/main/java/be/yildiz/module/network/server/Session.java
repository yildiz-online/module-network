/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildiz.module.network.server;

import be.yildiz.common.id.PlayerId;
import be.yildiz.common.log.Logger;
import be.yildiz.module.network.protocol.ServerResponse;
import lombok.Setter;

import java.util.Set;

/**
 * A session is a player when it is currently playing.
 *
 * @author Grégory Van den Borre
 */
public abstract class Session {

    /**
     * Associated player.
     */
    @Setter
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
        this.player = player;
        this.connected = true;
    }

    /**
     * Called when the client is disconnected.
     */
    public final void disconnect() {
        Logger.info(this.getPlayer() + " disconnected.");
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
    public abstract void sendMessage(ServerResponse message);

    /**
     * Send a list of messages to the connected client.
     *
     * @param messageList List of messages to send to the client.
     */
    public abstract void sendMessage(Set<ServerResponse> messageList);

    /**
     * Close the session if the client has been disconnected.
     */
    protected abstract void closeSession();

    /**
     * @return <code>true</code>.
     */
    public final boolean hasPlayer() {
        return true;
    }

    public PlayerId getPlayer() {
        return player;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isAuthenticated() {
        return authenticated;
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
