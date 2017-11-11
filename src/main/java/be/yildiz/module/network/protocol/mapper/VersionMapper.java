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
import be.yildizgames.common.mapping.IntegerMapper;
import be.yildizgames.common.mapping.MappingException;
import be.yildizgames.common.mapping.ObjectMapper;
import be.yildizgames.common.mapping.Separator;

/**
 * @author Grégory Van den Borre
 */
class VersionMapper implements ObjectMapper<Version> {

    private static final VersionMapper INSTANCE = new VersionMapper();

    private VersionMapper() {
        super();
    }

    public static VersionMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Version from(String s) throws MappingException {
        assert s!= null;
        String[] v = s.split(Separator.VAR_SEPARATOR);
        try {
            int major = IntegerMapper.getInstance().from(v[0]);
            int minor = IntegerMapper.getInstance().from(v[1]);
            int sub = IntegerMapper.getInstance().from(v[2]);
            int rev = IntegerMapper.getInstance().from(v[3]);
            Version.VersionType type = Version.VersionType.valueOf(IntegerMapper.getInstance().from(v[4]));
            return new Version(type, major, minor, sub, rev);
        } catch (NumberFormatException | IndexOutOfBoundsException | UnhandledSwitchCaseException e) {
            throw new MappingException(e);
        }
    }

    @Override
    public String to(Version version) {
        assert version != null;
        return IntegerMapper.getInstance().to(version.getMajor())
                + Separator.VAR_SEPARATOR
                + IntegerMapper.getInstance().to(version.getMinor())
                + Separator.VAR_SEPARATOR
                + IntegerMapper.getInstance().to(version.getSub())
                + Separator.VAR_SEPARATOR
                + IntegerMapper.getInstance().to(version.getRev())
                + Separator.VAR_SEPARATOR
                + IntegerMapper.getInstance().to(version.getType().value);
    }
}
