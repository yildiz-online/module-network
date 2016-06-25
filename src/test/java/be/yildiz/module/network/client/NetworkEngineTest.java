//      The MIT License (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
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

package be.yildiz.module.network.client;

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Grégory Van den Borre
 */
public class NetworkEngineTest {

    @Test
    public void testDelayMessageToNextFrame() {
        //FIXME implements
    }

    @Test
    public void testSendMessageServerRequest() {
        //FIXME implements
    }

    @Test
    public void testSendMessageString() {
        //FIXME implements
    }

    @Test
    public void testClose() {
        //FIXME implements
    }

    @Test
    public void testMessageReceivedAndUpdate() throws InvalidNetworkMessage {
        AbstractNetworkEngineClient ne = Mockito.spy(AbstractNetworkEngineClient.class);
        NetworkListener l = Mockito.mock(NetworkListener.class);
        ne.addNetworkListener(l);
        MessageWrapper mw = new MessageWrapper("a test");
        ne.messageReceived(mw);
        Mockito.verifyZeroInteractions(l);
        ne.update();
        Mockito.verify(l).parse(mw);
    }

    @Test
    public void testConnectionFailed() {
        AbstractNetworkEngineClient ne = Mockito.spy(AbstractNetworkEngineClient.class);
        NetworkListener l = Mockito.mock(NetworkListener.class);
        ne.addNetworkListener(l);
        Assert.assertFalse(ne.isConnected());
        ne.connectionFailed();
        Mockito.verify(l).connectionFailed();
        Assert.assertFalse(ne.isConnected());
    }

    @Test
    public void testConnectionLost() {
        AbstractNetworkEngineClient ne = Mockito.spy(AbstractNetworkEngineClient.class);
        NetworkListener l = Mockito.mock(NetworkListener.class);
        ne.addNetworkListener(l);
        Assert.assertFalse(ne.isConnected());
        ne.connectionSuccessful();
        Assert.assertTrue(ne.isConnected());
        ne.connectionLost();
        Mockito.verify(l).connectionLost();
        Assert.assertFalse(ne.isConnected());
    }

    @Test
    public void testIsConnected() {
        AbstractNetworkEngineClient ne = Mockito.spy(AbstractNetworkEngineClient.class);
        Assert.assertFalse(ne.isConnected());
        NetworkListener l = Mockito.mock(NetworkListener.class);
        ne.addNetworkListener(l);
        ne.connectionSuccessful();
        Mockito.verify(l).connected();
        Assert.assertTrue(ne.isConnected());
    }

    @Test
    public void testNetworkEngine() {

    }

}
