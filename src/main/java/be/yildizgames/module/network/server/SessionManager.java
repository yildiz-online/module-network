/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
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

import be.yildizgames.common.model.PlayerId;
import be.yildizgames.module.network.protocol.MessageWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Grégory Van den Borre
 */
public abstract class SessionManager {

    private static final System.Logger LOGGER = System.getLogger(SessionManager.class.getName());

    /**
     * List of listeners for sessions.
     */
    private final List<SessionListener> sessionListeners = new ArrayList<>();

    /**
     * Map to link the player to their associated session, those players may not be setAuthenticated.
     */
    private final Map<PlayerId, Session> connectedPlayerList = new HashMap<>();

    /**
     * Constant for a disconnected session.
     */
    private final Session disconnectedSession = new SessionManager.DisconnectedSession();

    /**
     * @return The list of all connected players.
     */
    public final Set<PlayerId> getActivePlayers() {
        return Collections.unmodifiableSet(this.connectedPlayerList.keySet());
    }

    /**
     * @return The list of all connected sessions.
     */
    public final List<Session> getActiveSessions() {
        return List.copyOf(this.connectedPlayerList.values());
    }

    /**
     * Retrieve a Session from a given Player.
     *
     * @param player Connected client.
     * @return The associated Session or a disconnected session if the Player is currently logged off.
     */
    public final Session getSessionByPlayer(final PlayerId player) {
        return this.connectedPlayerList.getOrDefault(player, this.disconnectedSession);
    }

    /**
     * Disconnect a session from the connected players.
     *
     * @param session Session to disconnect.
     */
    public final void disconnectSession(final Session session) {
        session.disconnect();
        this.connectedPlayerList.remove(session.getPlayer());
    }

    /**
     * Add a new session listener to be notified when messages are received or when client had setAuthenticated.
     *
     * @param listener SessionListener to add.
     */
    public final void addSessionListener(final SessionListener listener) {
        this.sessionListeners.add(listener);
    }

    /**
     * Callback for a received message, check if it is authenticated, if so, dispatch to the listeners, if not consider the message as an authentication request.
     *
     * @param session Session having sent the message.
     * @param message Message received.
     */
    public final void messageReceived(final Session session, final MessageWrapper message) {
        if (session.isAuthenticated()) {
            this.sessionListeners.forEach(l -> l.messageReceived(session, message));
        } else {
            authenticate(session, message);
        }
    }

    /**
     * Set a session as setAuthenticated and thus as connected.
     *
     * @param session Session to add.
     */
    public final void setAuthenticated(final Session session) {
        session.setAuthenticated();
        this.connectedPlayerList.put(session.getPlayer(), session);
        this.sessionListeners.forEach(l -> l.clientAuthenticated(session));
    }

    protected abstract void authenticate(Session session, MessageWrapper message);

    public abstract void update();

    /**
     * Simple class to use for disconnected players.
     *
     * @author Van den Borre Grégory
     */
    public static final class DisconnectedSession extends Session {

        /**
         * Simple constructor.
         */
        private DisconnectedSession() {
            super(PlayerId.valueOf(-5));
        }

        @Override
        public void sendMessage(final String message) {
            LOGGER.log(System.Logger.Level.DEBUG, "Not sending message(disconnected session): %s", message);
        }

        @Override
        protected void closeSession() {
            LOGGER.log(System.Logger.Level.DEBUG, "Not closing session(disconnected session)");
        }

        @Override
        public String toString() {
            return "Session: Disconnected.";
        }
    }
}
