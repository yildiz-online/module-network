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

import be.yildiz.common.Token;
import be.yildiz.common.Token.Status;
import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Common code for token messages.
 *
 * @author Grégory Van den Borre
 */
@Getter
@EqualsAndHashCode(callSuper = false)
abstract class AbstractTokenMessage extends NetworkMessage {

    /**
     * Authentication token.
     */
    private final Token token;

    /**
     * Full constructor.
     *
     * @param token Authentication token.
     */
    protected AbstractTokenMessage(final Token token) {
        super(convert(token));
        this.token = token;
    }

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    protected AbstractTokenMessage(MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        PlayerId id = this.getPlayerId();
        int key = this.getInt();
        Status status = Status.values()[this.getInt()];
        this.token = Token.any(id, key, status);
    }

    /**
     * Build the list of message parameters from a token.
     *
     * @param token Token to use.
     * @return A list of message parameters.
     */
    private static String[] convert(Token token) {
        return NetworkMessage.convertParams(token.getId(), Integer.valueOf(token.getKey()), token.getStatus().ordinal());
    }

}
