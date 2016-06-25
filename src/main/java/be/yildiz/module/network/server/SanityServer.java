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

import be.yildiz.common.log.Logger;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * A simple class to test a server socket on a given port.
 *
 * @author Grégory Van den Borre
 */
public class SanityServer {

    /**
     * Test to create a server socket on a given port and address.
     *
     * @param port    Socket port to test.
     * @param address Socket address to test.
     * @throws Exception If the socket creation failed.
     * @requires port >= 0 && port <= 65535
     * @requires address != null
     */
    public void test(final int port, final String address) throws Exception {
        Logger.info("Testing network host " + address + " port " + port + "...");
        ServerSocket ss = null;
        try {
            ss = new ServerSocket();
            ss.bind(new InetSocketAddress(address, 0));
            ss.close();
            Logger.info("Testing network host " + address + " port " + port + " successful.");
        } catch (Exception e) {
            Logger.error("Testing network host " + address + " port " + port + " error.");
            throw e;
        } finally {
            if (ss != null) {
                ss.close();
            }
        }
    }

}
