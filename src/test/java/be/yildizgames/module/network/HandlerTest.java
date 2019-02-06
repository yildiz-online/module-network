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

package be.yildizgames.module.network;

import be.yildizgames.module.network.exceptions.InvalidNetworkMessage;
import be.yildizgames.module.network.protocol.MessageWrapper;
import be.yildizgames.module.network.server.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Grégory Van den Borre
 */
public class HandlerTest {

    private final Session session = Mockito.mock(Session.class);

    @Test
    public void testProcessMessages1message() {
        TestHandler h = new TestHandler();
        h.processMessages(session, "1_aSmallTest");
        assertEquals(1, h.messages.size());
        assertEquals("aSmallTest", h.messages.get(0).content);
    }

    @Test
    public void testProcessMessagesEmptyMessage() {
        TestHandler h = new TestHandler();
        Assertions.assertThrows(InvalidNetworkMessage.class, () -> h.processMessages(session, ""));
    }

    @Test
    public void testProcessMessages3message() {
        TestHandler h = new TestHandler();
        h.processMessages(session, "&1_abc#&2_def#&3_ghi#");
        assertEquals(3, h.messages.size());
        assertEquals("abc", h.messages.get(0).content);
        assertEquals("def", h.messages.get(1).content);
        assertEquals("ghi", h.messages.get(2).content);
    }

    private static class TestHandler extends AbstractHandler {

        private final List<MessageWrapper> messages = new ArrayList<>();

        @Override
        protected void messageReceivedImpl(Session session, MessageWrapper message) {
            this.messages.add(message);
        }

    }

}
