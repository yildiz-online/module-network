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
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;
import be.yildiz.module.network.protocol.VersionCheck;

/**
 * @author Grégory Van den Borre
 */
public class VersionCheckMapper implements ObjectMapper<VersionCheck> {

    private final ObjectMapper<Version> mapper;

    private final ObjectMapper<Long> longMapper;

    public VersionCheckMapper(ObjectMapper<Version> mapper, ObjectMapper<Long> longMapper) {
        super();
        this.mapper = mapper;
        this.longMapper = longMapper;
    }

    @Override
    public VersionCheck from(String s) throws InvalidNetworkMessage {
        int sep = s.lastIndexOf(MessageSeparation.VAR_SEPARATOR);
        String vString = s.substring(0, sep);
        Version version = mapper.from(vString);
        long date = longMapper.from(s.substring(sep));
        return new VersionCheck(version, date);
    }

    @Override
    public String to(VersionCheck v) {
        return mapper.to(v.version) + MessageSeparation.VAR_SEPARATOR
                + longMapper.to(v.serverTime);
    }
}
