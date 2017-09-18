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
import be.yildiz.module.network.protocol.TemporaryAccountCreationResultDto;

/**
 * @author Grégory Van den Borre
 */
public class TemporaryAccountResultMapper implements ObjectMapper <TemporaryAccountCreationResultDto>{

    private static final TemporaryAccountResultMapper INSTANCE = new TemporaryAccountResultMapper();

    private TemporaryAccountResultMapper() {
        super();
    }

    public static TemporaryAccountResultMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public TemporaryAccountCreationResultDto from(String s) throws InvalidNetworkMessage {
        TemporaryAccountCreationResultDto dto = new TemporaryAccountCreationResultDto();
        try {
            String[] v = s.split(MessageSeparation.VAR_SEPARATOR);
            dto.setAccountExisting(BooleanMapper.getInstance().from(v[0]));
            dto.setEmailExisting(BooleanMapper.getInstance().from(v[1]));
            dto.setEmailMissing(BooleanMapper.getInstance().from(v[2]));
            dto.setEmailInvalid(BooleanMapper.getInstance().from(v[3]));
            dto.setInvalidLogin(BooleanMapper.getInstance().from(v[4]));
            dto.setInvalidPassword(BooleanMapper.getInstance().from(v[5]));
            dto.setTechnicalIssue(BooleanMapper.getInstance().from(v[6]));
            dto.setToken(v[7]);
            return dto;
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(TemporaryAccountCreationResultDto dto) {
        return BooleanMapper.getInstance().to(dto.isAccountExisting())
                + MessageSeparation.VAR_SEPARATOR
                + BooleanMapper.getInstance().to(dto.isEmailExisting())
                + MessageSeparation.VAR_SEPARATOR
                + BooleanMapper.getInstance().to(dto.isEmailMissing())
                + MessageSeparation.VAR_SEPARATOR
                + BooleanMapper.getInstance().to(dto.isEmailInvalid())
                + MessageSeparation.VAR_SEPARATOR
                + BooleanMapper.getInstance().to(dto.isInvalidLogin())
                + MessageSeparation.VAR_SEPARATOR
                + BooleanMapper.getInstance().to(dto.isInvalidPassword())
                + MessageSeparation.VAR_SEPARATOR
                + BooleanMapper.getInstance().to(dto.isTechnicalIssue())
                + MessageSeparation.VAR_SEPARATOR
                + dto.getToken();
    }
}
