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

import be.yildiz.common.Token;
import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class ConnectionRequestTest {

    private static final Token ok = new Token(PlayerId.WORLD, 0, 1, Token.Status.AUTHENTICATED);

    public static class Constructor {

        @Test
        public void happyFlow() {
            ConnectionRequest cr = new ConnectionRequest(ok);
            Assert.assertEquals(ok, cr.getToken());
        }

        @Test(expected = NullPointerException.class)
        public void withNull() {
            new ConnectionRequest((Token)null);
        }


        @Test
        public void happyFlowMessage() throws InvalidNetworkMessage {
            MessageWrapper mw = new MessageWrapper("10_0_1_0");
            ConnectionRequest cr = new ConnectionRequest(mw);
            Assert.assertEquals(ok, cr.getToken());
        }

        @Test(expected = InvalidNetworkMessage.class)
        public void withInvalidMessage() throws InvalidNetworkMessage {
            MessageWrapper mw = new MessageWrapper("10_0_0");
            new ConnectionRequest(mw);
        }

        @Test(expected = NullPointerException.class)
        public void withNullMessage() throws InvalidNetworkMessage {
            new ConnectionRequest((MessageWrapper) null);
        }
    }

    public static class Command {

        @Test
        public void happyFlow() {
            Assert.assertEquals(Commands.TOKEN_REQUEST, new ConnectionRequest(ok).command());
        }
    }
}
