/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
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

package be.yildizgames.module.network.server;

import be.yildizgames.common.model.PlayerId;
import be.yildizgames.module.network.protocol.NetworkMessage;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Grégory Van den Borre
 */
public class SessionTest {

    @Nested
    public class Constructor {

        @Test
        public void happyFlow() {
            Session s = givenASession(PlayerId.valueOf(5));
            assertEquals(PlayerId.valueOf(5), s.getPlayer());
            assertTrue(s.isConnected());
            assertFalse(s.isAuthenticated());
        }

        @Test
        public void withNull() {
            assertThrows(NullPointerException.class, () -> givenASession(null));
        }

    }

    @Nested
    public class HasPlayer {

        @Test
        public void happyFlow() {
            Session s = givenASession(PlayerId.valueOf(5));
            assertTrue(s.hasPlayer());
        }
    }

    @Nested
    public class SetAuthenticated {

        @Test
        public void happyFlow() {
            Session s = givenASession(PlayerId.valueOf(5));
            assertFalse(s.isAuthenticated());
            s.setAuthenticated();
            assertTrue(s.isAuthenticated());
        }
    }

    @Nested
    public class SetPlayer {

        @Test
        public void happyFlow() {
            Session s = givenASession(PlayerId.valueOf(5));
            s.setPlayer(PlayerId.valueOf(8));
            assertEquals(PlayerId.valueOf(8), s.getPlayer());
        }

        @Test
        public void withNull() {
            Session s = givenASession(PlayerId.valueOf(5));
            assertThrows(NullPointerException.class, () -> s.setPlayer(null));
        }

    }

    @Nested
    public class SendMessage {

        @Test
        public void happyFlow() {
            SessionWrapper sw = givenASession(PlayerId.valueOf(5));
            sw.sendMessage("test");
            assertEquals("test", sw.getMessage());
        }

        @Test
        public void withServerResponse() {
            SessionWrapper sw = givenASession(PlayerId.valueOf(5));
            sw.sendMessage("someMessage");
            assertEquals("someMessage", sw.getMessage());
        }

        @Test
        public void withNullServerResponse() {
            SessionWrapper sw = givenASession(PlayerId.valueOf(5));
            assertThrows(NullPointerException.class, () -> sw.sendMessage((NetworkMessage<?>) null));
        }

        @Test
        public void withNullSeveralServerResponse() {
            SessionWrapper sw = givenASession(PlayerId.valueOf(5));
            assertThrows(NullPointerException.class, () -> sw.sendMessage((Set<NetworkMessage<?>>) null));
        }
    }

    public static SessionWrapper givenASession(PlayerId p) {
        return new SessionWrapper(p);
    }

    private static class SessionWrapper extends Session {

        private String message;

        SessionWrapper(PlayerId p) {
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

        String getMessage() {
            return message;
        }
    }
}