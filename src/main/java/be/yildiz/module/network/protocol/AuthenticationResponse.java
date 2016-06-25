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

package be.yildiz.module.network.protocol;

import be.yildiz.common.Token;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;

/**
 * Server response for an authentication request, contains the authentication state on server.
 *
 * @author Grégory Van den Borre
 */
public final class AuthenticationResponse extends AbstractTokenMessage implements ServerResponse {

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public AuthenticationResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
    }

    /**
     * Full constructor.
     *
     * @param token Authentication token.
     */
    public AuthenticationResponse(final Token token) {
        super(token);
    }

    /**
     * @return value of Commands.AUTHENTICATION_RESPONSE.
     */
    @Override
    public int command() {
        return Commands.AUTHENTICATION_RESPONSE;
    }
}
