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

import be.yildiz.common.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class AuthenticationTest {

    public static class Constructor {

        @Test
        public void happyFlow() {
            Authentication auth = new Authentication("ok", "ok2");
            Assert.assertEquals("ok", auth.login);
            Assert.assertEquals("ok2", auth.password);
        }

        @Test(expected = AssertionError.class)
        public void nullLogin() {
            new Authentication(null, "ok");
        }

        @Test(expected = AssertionError.class)
        public void nullPassword() {
            new Authentication("ok", null);
        }
    }

    public static class EqualsHashcode {

        @Test
        public void test() {
            Authentication base = new Authentication("ok", "ok2");
            Authentication same = new Authentication("ok", "ok2");
            Authentication diff = new Authentication("ok", "ok");
            BaseTest<Authentication> bt = new BaseTest<>(base, same, diff);
            bt.all();
        }
    }
}