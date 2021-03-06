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

import be.yildizgames.module.network.exceptions.NetworkException;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * A simple class to test a server socket on a given port.
 *
 * @author Grégory Van den Borre
 */
public class SanityServer {

    private static final System.Logger LOGGER = System.getLogger(SanityServer.class.getName());

    /**
     * Private to prevent instantiation.
     */
    private SanityServer() {
        super();
    }

    /**
     * Test to create a server socket on a given port and address.
     *
     * @param port    Socket port to test.
     * @param address Socket address to test.
     */
    //@requires port >= 0 && port <= 65535
    //@requires address != null
    public static void test(final int port, final String address) {
        LOGGER.log(System.Logger.Level.DEBUG, "Testing network host {0}, port {1}...", address, port);
        try (ServerSocket ss = new ServerSocket()){
            ss.bind(new InetSocketAddress(address, 0));
            LOGGER.log(System.Logger.Level.DEBUG, "Connexion to host {0}, port {1} successful.", address, port);
        } catch (Exception e) {
            LOGGER.log(System.Logger.Level.ERROR, "Connexion to host {0}, port {1} failure.", address, port);
            throw new NetworkException("Connexion failure");
        }
    }

}
