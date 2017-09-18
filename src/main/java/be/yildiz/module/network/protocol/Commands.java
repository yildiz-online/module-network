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

package be.yildiz.module.network.protocol;

/**
 * List of generic command values.
 *
 * @author Grégory Van den Borre
 */
public final class Commands {

    /**
     * Client request to the authentication server for an authentication.
     */
    public static final int AUTHENTICATION_REQUEST = 10;

    /**
     * Authentication server response for an authentication to the client.
     */
    public static final int AUTHENTICATION_RESPONSE = 99;

    /**
     * Client request to connect to the game server.
     */
    public static final int CONNECTION_REQUEST = 25;

    /**
     * Game server request to check a token validity to the authentication server.
     */
    public static final int TOKEN_VERIFICATION_REQUEST = 98;

    /**
     * Authentication server response to check a token validity.
     */
    public static final int TOKEN_VERIFICATION_RESPONSE = 98;

    /**
     * Server response for the version.
     */
    public static final int VERSION_RESPONSE = 0;

    public static final int ACCOUNT_CREATION = 96;

    public static final int ACCOUNT_VALIDATION = 97;

    /**
     * Private constructor to prevent instantiations.
     */
    private Commands() {
        super();
    }

}
