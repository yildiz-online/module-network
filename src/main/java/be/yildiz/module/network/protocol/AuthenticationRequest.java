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

package be.yildiz.module.network.protocol;

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import lombok.Getter;

/**
 * A request from the client to the server to authenticate a Player.
 *
 * @author Grégory Van den Borre
 */
public final class AuthenticationRequest extends NetworkMessage implements ServerRequest {

    /**
     * User login.
     */
    @Getter
    private final String login;

    /**
     * User encrypted password.
     */
    @Getter
    private final String password;

    /**
     * Create a new authentication request from a user login and password.
     *
     * @param userLogin    User login.
     * @param userPassword User password.
     * @throws NullPointerException if login or password is null.
     */
    public AuthenticationRequest(final String userLogin, final String userPassword) {
        super(NetworkMessage.convertParams(userLogin, userPassword));
        this.login = userLogin;
        this.password = userPassword;
    }

    /**
     * Create a new instance from a message.
     *
     * @param message Message to parse to build the object.
     * @throws InvalidNetworkMessage If the message cannot be correctly parsed.
     * @throws NullPointerException If message is null
     */
    //@requires message != null
    public AuthenticationRequest(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.login = this.getString();
        this.password = this.getString();
    }

    /**
     * @return value of Commands.AUTHENTICATION_REQUEST.
     */
    @Override
    public int command() {
        return Commands.AUTHENTICATION_REQUEST;
    }
}
