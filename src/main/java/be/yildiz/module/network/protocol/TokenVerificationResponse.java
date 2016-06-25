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

import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import lombok.Getter;

/**
 * Authentication token verification response.
 *
 * @author Grégory Van den Borre
 */
@Getter
public final class TokenVerificationResponse extends NetworkMessage implements ServerResponse {

    /**
     * Player checked.
     */
    private final PlayerId id;

    /**
     * Player authentication status.
     */
    private final boolean authenticated;

    /**
     * Create a new message from a player and a status.
     *
     * @param id            Player's id.
     * @param authenticated <code>true</code> if the player is setAuthenticated, <code>false</code> otherwise.
     */
    public TokenVerificationResponse(final PlayerId id, final boolean authenticated) {
        super(convertParams(id, authenticated));
        this.id = id;
        this.authenticated = authenticated;
    }

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public TokenVerificationResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.id = this.getPlayerId();
        this.authenticated = this.getBoolean();
    }

    @Override
    public int command() {
        return Commands.TOKEN_VERIFICATION_RESPONSE;
    }

}
