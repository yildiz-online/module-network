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

package be.yildiz.module.network.protocol.mapper;

import be.yildiz.common.Version;
import be.yildiz.common.exeption.UnhandledSwitchCaseException;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;

/**
 * @author Grégory Van den Borre
 */
public class VersionMapper implements ObjectMapper<Version> {

    private final ObjectMapper<Integer> integerMapper;

    public VersionMapper(ObjectMapper<Integer> integerMapper) {
        super();
        assert integerMapper != null;
        this.integerMapper = integerMapper;
    }

    @Override
    public Version from(String s) throws InvalidNetworkMessage {
        assert s!= null;
        String[] v = s.split(MessageSeparation.VAR_SEPARATOR);
        try {
            int major = integerMapper.from(v[0]);
            int minor = integerMapper.from(v[1]);
            int sub = integerMapper.from(v[2]);
            int rev = integerMapper.from(v[3]);
            Version.VersionType type = Version.VersionType.valueOf(integerMapper.from(v[4]));
            return new Version(type, major, minor, sub, rev);
        } catch (NumberFormatException | IndexOutOfBoundsException | UnhandledSwitchCaseException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(Version version) {
        assert version != null;
        return integerMapper.to(version.getMajor())
                + MessageSeparation.VAR_SEPARATOR
                + integerMapper.to(version.getMinor())
                + MessageSeparation.VAR_SEPARATOR
                + integerMapper.to(version.getSub())
                + MessageSeparation.VAR_SEPARATOR
                + integerMapper.to(version.getRev())
                + MessageSeparation.VAR_SEPARATOR
                + integerMapper.to(version.getType().value);
    }
}