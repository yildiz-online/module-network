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
import be.yildiz.common.authentication.Credentials;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.mapper.*;

/**
 * @author Grégory Van den Borre
 */
public class NetworkMessageFactory {

    public NetworkMessage<TokenVerification> tokenVerified(TokenVerification t) {
        return new NetworkMessage<>(t, TokenVerificationMapper.getInstance(), Commands.TOKEN_VERIFICATION_RESPONSE);
    }

    public TokenVerification tokenVerified(MessageWrapper message) throws InvalidNetworkMessage {
        return new NetworkMessage<>(message, TokenVerificationMapper.getInstance(), Commands.TOKEN_VERIFICATION_RESPONSE).getDto();
    }

    public NetworkMessage<Token>connectionRequest(Token t) {
        return new NetworkMessage<>(t, TokenMapper.getInstance(), Commands.CONNECTION_REQUEST);
    }

    /**
     * Client requesting the server to connect, authenticating with the token received from the authentication server.
     * @param message Request to send.
     * @return The dto in the message.
     * @throws InvalidNetworkMessage If the message is not correctly formatted.
     */
    public Token connectionRequest(MessageWrapper message) throws InvalidNetworkMessage {
        return new NetworkMessage<>(message, TokenMapper.getInstance(), Commands.CONNECTION_REQUEST).getDto();
    }

    public NetworkMessage<Token>tokenVerification(Token t) {
        return new NetworkMessage<>(t, TokenMapper.getInstance(), Commands.TOKEN_VERIFICATION_REQUEST);
    }

    /**
     * Server requesting to the authentication for a token verification.
     * @param message Request to send.
     * @return The dto in the message.
     * @throws InvalidNetworkMessage If the message is not correctly formatted.
     */
    public Token tokenVerification(MessageWrapper message) throws InvalidNetworkMessage {
        return new NetworkMessage<>(message, TokenMapper.getInstance(), Commands.TOKEN_VERIFICATION_REQUEST).getDto();
    }

    public NetworkMessage<VersionCheck> versionRequest(VersionCheck v) {
        return new NetworkMessage<>(v, VersionCheckMapper.getInstance(), 0);
    }

    public NetworkMessage<VersionCheck> versionRequest(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, VersionCheckMapper.getInstance(), 0);
    }


    public NetworkMessage<Credentials> authenticationRequest(Credentials dto) {
        return new NetworkMessage<>(dto, CredentialsMapper.getInstance(), Commands.AUTHENTICATION_REQUEST);
    }

    public Credentials authenticationRequest(MessageWrapper message) throws InvalidNetworkMessage {
        return new NetworkMessage<>(message, CredentialsMapper.getInstance(), Commands.AUTHENTICATION_REQUEST).getDto();
    }

    public NetworkMessage<Token> authenticationResponse(Token t) {
        return new NetworkMessage<>(t, TokenMapper.getInstance(), Commands.AUTHENTICATION_RESPONSE);
    }

    public Token authenticationResponse(MessageWrapper message) throws InvalidNetworkMessage {
        return new NetworkMessage<>(message, TokenMapper.getInstance(), Commands.AUTHENTICATION_RESPONSE).getDto();
    }

    public NetworkMessage<TemporaryAccountDto> accountCreation(TemporaryAccountDto dto) throws InvalidNetworkMessage {
        return new NetworkMessage<>(dto, TemporaryAccountMapper.getInstance(), Commands.ACCOUNT_CREATION);
    }

    public TemporaryAccountDto accountCreation(MessageWrapper message) throws InvalidNetworkMessage {
        return new NetworkMessage<>(message, TemporaryAccountMapper.getInstance(), Commands.ACCOUNT_CREATION).getDto();
    }

    public NetworkMessage<TemporaryAccountCreationResultDto> accountCreationResult(TemporaryAccountCreationResultDto result) {
        return new NetworkMessage<>(result, TemporaryAccountResultMapper.getInstance(), Commands.ACCOUNT_CREATION);
    }

    public AccountValidationDto accountValidation(MessageWrapper message) throws InvalidNetworkMessage {
        return new NetworkMessage<>(message, AccountValidationMapper.getInstance(), Commands.ACCOUNT_VALIDATION).getDto();
    }

    public NetworkMessage<AccountValidationDto> accountValidation(AccountValidationDto dto) {
        return new NetworkMessage<>(dto, AccountValidationMapper.getInstance(), Commands.ACCOUNT_VALIDATION);
    }
}
