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

import be.yildiz.common.Version;
import be.yildiz.common.Version.VersionType;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import lombok.Getter;

/**
 * Send the version data and the time correction to the client.
 *
 * @author Grégory Van den Borre
 */
public final class VersionResponse extends NetworkMessage implements ServerResponse {

    /**
     * Expected version of the client.
     */
    @Getter
    private final Version version;

    /**
     * Server current time when sending the message to compute difference in client.
     */
    @Getter
    private final long serverTime;

    /**
     * Full constructor, parse the message to build the object.
     *
     * @param message Message received from the server.
     * @throws InvalidNetworkMessage in case of error while parsing the message.
     */
    public VersionResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.version = new Version(VersionType.values()[this.getInt()], this.getInt(), this.getInt(), this.getInt(), this.getInt());
        this.serverTime = this.getLong();
    }

    /**
     * Full constructor.
     *
     * @param expectedVersion Expected client version.
     * @param currentTime     Time stamp on server to make correction on client if time is not the same.
     */
    public VersionResponse(final Version expectedVersion, final long currentTime) {
        super(NetworkMessage.convertParams(Integer.valueOf(expectedVersion.getType().ordinal()), Integer.valueOf(expectedVersion.getMajor()), Integer.valueOf(expectedVersion.getMinor()),
                Integer.valueOf(expectedVersion.getSub()), Integer.valueOf(expectedVersion.getRev()), Long.valueOf(currentTime)));
        this.version = expectedVersion;
        this.serverTime = currentTime;
    }

    /**
     * @return The ordinal value of ServerCommand VERSION.
     */
    @Override
    public int command() {
        return Commands.VERSION_RESPONSE;
    }
}
