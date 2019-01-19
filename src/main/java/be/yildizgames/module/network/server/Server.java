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

import be.yildizgames.module.network.DecoderEncoder;
import be.yildizgames.module.network.exceptions.NetworkException;
import be.yildizgames.module.network.server.dummy.DummyServerProvider;

import java.util.ServiceLoader;

/**
 * A server will wait for and accept connections from clients.
 *
 * @author Grégory Van den Borre
 */
public abstract class Server {

    public static Server getEngine() {
        ServiceLoader<ServerProvider> provider = ServiceLoader.load(ServerProvider.class);
        return provider.findFirst().orElseGet(DummyServerProvider::new).getEngine();
    }

    /**
     * Initialize and start the server.
     * @param address Address to connect to.
     * @param port Port to connect to.
     * @param sessionManager Session manager to handle connexions.
     * @param codec Transmission codec.
     */
    public abstract void startServer(String address, int port, SessionManager sessionManager, DecoderEncoder codec);

    /**
     * Initialize and start the server.
     * @param port Port to connect to.
     * @param sessionManager Session manager to handle connexions.
     * @param codec Transmission codec.
     */
    public abstract void startServer(int port, SessionManager sessionManager, DecoderEncoder codec);

    protected final void throwError(String message) {
        throw new NetworkException(message);
    }

    protected final void throwError(String message, Exception e) {
        throw new NetworkException(message, e);
    }

}
