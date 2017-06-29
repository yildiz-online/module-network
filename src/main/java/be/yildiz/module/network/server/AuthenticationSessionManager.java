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

import be.yildiz.common.Token;
import be.yildiz.module.network.AuthenticationConfiguration;
import be.yildiz.module.network.client.AbstractNetworkEngineClient;
import be.yildiz.module.network.protocol.NetworkMessageFactory;

/**
 * Manage the sessions, only setAuthenticated session are stored.
 *
 * @author Grégory Van den Borre
 */
public final class AuthenticationSessionManager extends SessionManager {

    /**
     * Network client engine.
     */
    private final AbstractNetworkEngineClient client;

    private final NetworkMessageFactory factory = new NetworkMessageFactory();

    /**
     * Create a new AuthenticationSessionManager.
     *
     * @param client Client to be connected to the authentication server.
     * @param config Contains the connection configuration.
     */
    public AuthenticationSessionManager(final AbstractNetworkEngineClient client, final AuthenticationConfiguration config) {
        super();
        this.client = client;
        this.client.addNetworkListener(message -> {
            TokenVerificationResponse r = new TokenVerificationResponse(message);
            if (r.getVerification().authenticated) {
                Session session = getSessionByPlayer(r.getVerification().playerId);
                setAuthenticated(session);
            }
        });
        this.client.connect(config);
    }

    /**
     * Update the status of the client connected to the authentication server.
     */
    @Override
    public void update() {
        this.client.update();
    }

    /**
     * Check if the authentication request is valid and notify the listeners if it is.
     *
     * @param request Received AuthenticationRequest.
     */
    //@requires request != null
    @Override
    public final void authenticate(final Token request) {
        this.client.sendMessage(factory.request(request).buildMessage());
    }
}
