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

package be.yildiz.module.network.server;

import be.yildiz.module.network.protocol.MessageWrapper;

/**
 * Notify observers when a session event occurs.
 *
 * @author Grégory Van den Borre
 */
@FunctionalInterface
public interface SessionListener {

    /**
     * Called when a message is received from the client.
     *
     * @param session Session having sent the message.
     * @param message Message received.
     */
    void messageReceived(Session session, MessageWrapper message);

    /**
     * Called when a client is successfully setAuthenticated.
     *
     * @param session Client session.
     */
    default void clientAuthenticated(Session session) {
    }

    /**
     * Called when a client is disconnected.
     *
     * @param session Client session.
     */
    default void sessionClosed(Session session) {
    }

}
