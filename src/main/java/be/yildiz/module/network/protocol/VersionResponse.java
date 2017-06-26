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
import be.yildiz.module.network.protocol.mapper.IntegerMapper;
import be.yildiz.module.network.protocol.mapper.LongMapper;
import be.yildiz.module.network.protocol.mapper.VersionCheckMapper;
import be.yildiz.module.network.protocol.mapper.VersionMapper;

/**
 * Send the version data and the time correction to the client.
 *
 * @author Grégory Van den Borre
 */
public final class VersionResponse extends NetworkMessage implements ServerResponse {

    static {
        NetworkMessage.registerMapper(VersionCheck.class,
                new VersionCheckMapper(new VersionMapper(new IntegerMapper()), new LongMapper()));
    }

    /**
     * Expected version of the client.
     */
    private final VersionCheck version;

    /**
     * Full constructor, parse the message to build the object.
     *
     * @param message Message received from the server.
     * @throws InvalidNetworkMessage in case of error while parsing the message.
     */
    public VersionResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.version = this.from(VersionCheck.class);
    }

    /**
     * Full constructor.
     *
     * @param expectedVersion Expected client version and server time.
     */
    public VersionResponse(final VersionCheck expectedVersion) {
        super(NetworkMessage.to(expectedVersion, VersionCheck.class));
        this.version = expectedVersion;
    }

    /**
     * @return The ordinal value of ServerCommand VERSION.
     */
    @Override
    public int command() {
        return Commands.VERSION_RESPONSE;
    }

    public VersionCheck getVersion() {
        return version;
    }
}
