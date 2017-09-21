/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2017 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildiz.module.network.protocol.mapper;

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;
import be.yildiz.module.network.protocol.TemporaryAccountDto;

/**
 * @author Grégory Van den Borre
 */
public class TemporaryAccountMapper implements ObjectMapper<TemporaryAccountDto> {

    private static final TemporaryAccountMapper INSTANCE = new TemporaryAccountMapper();

    private TemporaryAccountMapper() {
        super();
    }

    public static TemporaryAccountMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public TemporaryAccountDto from(final String s) throws InvalidNetworkMessage {
        assert s != null;
        try {
            String[] v = s.split(MessageSeparation.OBJECTS_SEPARATOR);
            return new TemporaryAccountDto(v[0], v[1], v[2]);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(final TemporaryAccountDto dto) {
        assert dto != null;
        return dto.getLogin()
                + MessageSeparation.OBJECTS_SEPARATOR
                + dto.getPassword()
                + MessageSeparation.OBJECTS_SEPARATOR
                + dto.getEmail();
    }
}
