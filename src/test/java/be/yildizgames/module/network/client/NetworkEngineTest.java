/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
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

package be.yildizgames.module.network.client;

import be.yildizgames.module.network.client.dummy.DummyNetworkEngine;
import be.yildizgames.module.network.exceptions.InvalidNetworkMessage;
import be.yildizgames.module.network.protocol.MessageWrapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Grégory Van den Borre
 */
class NetworkEngineTest {

    @Test
    void testDelayMessageToNextFrame() {
        //FIXME implements
    }

    @Test
    void testSendMessageServerRequest() {
        //FIXME implements
    }

    @Test
    void testSendMessageString() {
        //FIXME implements
    }

    @Test
    void testClose() {
        //FIXME implements
    }

    @Test
    void testMessageReceivedAndUpdate() throws InvalidNetworkMessage {
        NetworkEngineClient ne = new DummyNetworkEngine();
        NetworkListener l = Mockito.mock(NetworkListener.class);
        ne.addNetworkListener(l);
        MessageWrapper mw = new MessageWrapper("a test");
        ne.messageReceived(mw);
        Mockito.verifyZeroInteractions(l);
        ne.update();
        Mockito.verify(l).parse(mw);
    }

    @Test
    void testConnectionFailed() {
        NetworkEngineClient ne = new DummyNetworkEngine();
        NetworkListener l = Mockito.mock(NetworkListener.class);
        ne.addNetworkListener(l);
        assertFalse(ne.isConnected());
        ne.connectionFailed();
        Mockito.verify(l).connectionFailed();
        assertFalse(ne.isConnected());
    }

    @Test
    void testConnectionLost() {
        NetworkEngineClient ne = new DummyNetworkEngine();
        NetworkListener l = Mockito.mock(NetworkListener.class);
        ne.addNetworkListener(l);
        assertFalse(ne.isConnected());
        ne.connectionSuccessful();
        assertTrue(ne.isConnected());
        ne.connectionLost();
        Mockito.verify(l).connectionLost();
        assertFalse(ne.isConnected());
    }

    @Test
    void testIsConnected() {
        NetworkEngineClient ne = new DummyNetworkEngine();
        assertFalse(ne.isConnected());
        NetworkListener l = Mockito.mock(NetworkListener.class);
        ne.addNetworkListener(l);
        ne.connectionSuccessful();
        Mockito.verify(l).connected();
        assertTrue(ne.isConnected());
    }

    @Test
    void testDisconnect() {
        NetworkEngineClient ne = new DummyNetworkEngine();
        ne.connectionSuccessful();
        assertTrue(ne.isConnected());
        ne.disconnect();
        //Still true as no listener has notified that the connection is indeed lost.
        assertTrue(ne.isConnected());
    }

    @Test
    void testNetworkEngine() {

    }

}
