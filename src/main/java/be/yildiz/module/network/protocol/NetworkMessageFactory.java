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
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.mapper.TokenMapper;
import be.yildiz.module.network.protocol.mapper.TokenVerificationMapper;
import be.yildiz.module.network.protocol.mapper.VersionCheckMapper;

/**
 * @author Grégory Van den Borre
 */
public class NetworkMessageFactory {

    public NetworkMessage<TokenVerification> message(TokenVerification t) {
        return new NetworkMessage<>(t, TokenVerificationMapper.getInstance(), 98);
    }

    public NetworkMessage<Token>check(Token t) {
        return new NetworkMessage<>(t, TokenMapper.getInstance(), 98);
    }

    public NetworkMessage<Token>connect(Token t) {
        return new NetworkMessage<>(t, TokenMapper.getInstance(), 25);
    }

    public NetworkMessage<VersionCheck> message(VersionCheck v) {
        return new NetworkMessage<>(v, VersionCheckMapper.getInstance(), 0);
    }

    public NetworkMessage<Token> request(Token t) {
        return new NetworkMessage<>(t, TokenMapper.getInstance(), 10);
    }

    public NetworkMessage<Token> response(Token t) {
        return new NetworkMessage<>(t, TokenMapper.getInstance(), 99);
    }

    public TokenVerification getTokenVerification(MessageWrapper message) throws InvalidNetworkMessage {
        return new NetworkMessage<>(message, TokenVerificationMapper.getInstance()).getDto();
    }

    public Token getTokenRequest(MessageWrapper message) throws InvalidNetworkMessage {
        return new NetworkMessage<>(message, TokenMapper.getInstance()).getDto();
    }
}
