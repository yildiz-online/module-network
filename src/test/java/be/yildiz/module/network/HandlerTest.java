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

package be.yildiz.module.network;

import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.server.Session;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class HandlerTest {

    private final Session session = Mockito.mock(Session.class);

    @Test
    public void testProcessMessages1message() {
        TestHandler h = new TestHandler();
        h.processMessages(session, "aSmallTest");
        Assert.assertEquals(1, h.messages.size());
        Assert.assertEquals("aSmallTest", h.messages.get(0).message);
    }

    @Test
    public void testProcessMessagesEmptyMessage() {
        TestHandler h = new TestHandler();
        h.processMessages(session, "");
        Assert.assertEquals(1, h.messages.size());
        Assert.assertEquals("", h.messages.get(0).message);
    }

    @Test
    public void testProcessMessages3message() {
        TestHandler h = new TestHandler();
        h.processMessages(session, "&abc#&def#&ghi#");
        Assert.assertEquals(3, h.messages.size());
        Assert.assertEquals("abc", h.messages.get(0).message);
        Assert.assertEquals("def", h.messages.get(1).message);
        Assert.assertEquals("ghi", h.messages.get(2).message);
    }

    private static class TestHandler extends AbstractHandler {

        private final List<MessageWrapper> messages = new ArrayList<>();

        @Override
        protected void messageReceivedImpl(Session session, MessageWrapper message) {
            this.messages.add(message);
        }

    }

}
