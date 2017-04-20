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

package be.yildiz.module.network.server;

import be.yildiz.common.collections.Sets;
import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.protocol.ServerResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Set;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class SessionTest {

    public static class Constructor {

        @Test
        public void happyFlow() {
            Session s = givenASession(PlayerId.get(5));
            Assert.assertEquals(PlayerId.get(5), s.getPlayer());
            Assert.assertTrue(s.isConnected());
            Assert.assertFalse(s.isAuthenticated());
        }

        @Test(expected = AssertionError.class)
        public void withNull() {
            givenASession(null);
        }

    }

    public static class HasPlayer {

        @Test
        public void happyFlow() {
            Session s = givenASession(PlayerId.get(5));
            Assert.assertTrue(s.hasPlayer());
        }
    }

    public static class SetAuthenticated {

        @Test
        public void happyFlow() {
            Session s = givenASession(PlayerId.get(5));
            Assert.assertFalse(s.isAuthenticated());
            s.setAuthenticated();
            Assert.assertTrue(s.isAuthenticated());
        }
    }

    public static class SetPlayer {

        @Test
        public void happyFlow() {
            Session s = givenASession(PlayerId.get(5));
            s.setPlayer(PlayerId.get(8));
            Assert.assertEquals(PlayerId.get(8), s.getPlayer());
        }

        @Test(expected = AssertionError.class)
        public void withNull() {
            Session s = givenASession(PlayerId.get(5));
            s.setPlayer(null);
        }

    }

    public static class SendMessage {

        @Test
        public void happyFlow() {
            SessionWrapper sw = givenASession(PlayerId.get(5));
            sw.sendMessage("test");
            Assert.assertEquals("test", sw.getMessage());
        }

        @Test
        public void withServerResponse() {
            SessionWrapper sw = givenASession(PlayerId.get(5));
            sw.sendMessage(() -> "someMessage");
            Assert.assertEquals("someMessage", sw.getMessage());
        }

        @Test(expected = AssertionError.class)
        public void withNullServerResponse() {
            SessionWrapper sw = givenASession(PlayerId.get(5));
            sw.sendMessage((ServerResponse) null);
        }

        @Test
        public void withSeveralServerResponse() {
            SessionWrapper sw = givenASession(PlayerId.get(5));
            Set<ServerResponse> responses = Sets.newSet(() -> "1", () -> "2", () -> "3");
            sw.sendMessage(responses);
        }

        @Test(expected = AssertionError.class)
        public void withNullSeveralServerResponse() {
            SessionWrapper sw = givenASession(PlayerId.get(5));
            sw.sendMessage((Set<ServerResponse>) null);
        }
    }

    private static SessionWrapper givenASession(PlayerId p) {
        return new SessionWrapper(p);
    }

    private static class SessionWrapper extends Session {

        private String message;

        public SessionWrapper(PlayerId p) {
            super(p);
        }

        @Override
        protected void closeSession() {
            //does nothing
        }

        @Override
        public void sendMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}