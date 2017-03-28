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
import be.yildiz.common.id.PlayerId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class AbstractTokenMessageTest {

    public static class GetToken {

        @Test
        public void happyFlow() {
            Token t = Token.authenticated(PlayerId.WORLD, 10L, 5);
            AbstractTokenMessage message = givenAMessage(t);
            Assert.assertEquals(t,message.getToken());
        }
    }

    public static class Equals {

        @Test
        public void sameValue() {
            Token t = Token.authenticated(PlayerId.WORLD, 10L, 5);
            AbstractTokenMessage message = givenAMessage(t);
            AbstractTokenMessage message2 = givenAMessage(t);
            Assert.assertTrue(message.equals(message2));
        }

        @Test
        public void sameInstance() {
            Token t = Token.authenticated(PlayerId.WORLD, 10L, 5);
            AbstractTokenMessage message = givenAMessage(t);
            Assert.assertTrue(message.equals(message));
        }

        @Test
        public void differentValue() {
            Token t = Token.authenticated(PlayerId.WORLD, 10L, 5);
            AbstractTokenMessage message = givenAMessage(t);
            Token t2 = Token.authenticated(PlayerId.get(1), 10L, 5);
            AbstractTokenMessage message2 = givenAMessage(t2);
            Assert.assertNotEquals(message, message2);
        }

        @Test
        public void withNull() {
            Token t = Token.authenticated(PlayerId.WORLD, 10L, 5);
            AbstractTokenMessage message = givenAMessage(t);
            Assert.assertNotEquals(message, null);
        }

    }

    public static class HashCode {

        @Test
        public void sameValue() {
            Token t = Token.authenticated(PlayerId.WORLD, 10L, 5);
            AbstractTokenMessage message = givenAMessage(t);
            AbstractTokenMessage message2 = givenAMessage(t);
            Assert.assertTrue(message.hashCode() == message2.hashCode());
        }

        @Test
        public void differentValue() {
            Token t = Token.authenticated(PlayerId.WORLD, 10L, 5);
            AbstractTokenMessage message = givenAMessage(t);
            Token t2 = Token.authenticated(PlayerId.get(1), 10L, 5);
            AbstractTokenMessage message2 = givenAMessage(t2);
            Assert.assertFalse(message.hashCode() == message2.hashCode());
        }
    }

    private static AbstractTokenMessage givenAMessage(Token t) {
        return new AbstractTokenMessage(t) {
            @Override
            public int command() {
                return 0;
            }
        };
    }
}