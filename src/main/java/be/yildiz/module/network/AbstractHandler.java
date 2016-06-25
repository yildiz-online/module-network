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

package be.yildiz.module.network;

import be.yildiz.common.log.Logger;
import be.yildiz.module.network.protocol.MessageSeparation;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.server.Session;
import lombok.NoArgsConstructor;

/**
 * The handler controls the way the network will react to incoming message in its concrete implementation, the abstract part will manage the message construction.
 *
 * @author Grégory Van den Borre
 */
@NoArgsConstructor
public abstract class AbstractHandler {

    /**
     * Process the messages by splitting in unique messages, and for each of them, calling the appropriate logic.
     *
     * @param session Session having received the message.
     * @param message Message received.
     * @requires session != null
     * @requires message != null
     */
    public void processMessages(final Session session, final String message) {
        Logger.info("Unprocessed network message: " + message);
        String messageWithoutStartChar = message.replaceAll(MessageSeparation.MESSAGE_BEGIN, "");
        String[] messages = messageWithoutStartChar.split(MessageSeparation.MESSAGE_END);
        for (String c : messages) {
            this.messageReceivedImpl(session, new MessageWrapper(c));
        }
    }

    /**
     * Call the implementation logic to handle this message.
     *
     * @param session Session having received the message.
     * @param message Message received.
     * @requires session != null
     * @requires message != null
     */
    protected abstract void messageReceivedImpl(Session session, MessageWrapper message);

}
