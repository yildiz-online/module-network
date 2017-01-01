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

import be.yildiz.common.collections.Lists;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Grégory Van Den Borre
 */
public class NetworkMessageTest {

    @Test
    public void testGetList() throws InvalidNetworkMessage {
        List<Float> lf = Lists.newList();
        lf.add(Float.valueOf(10f));
        lf.add(Float.valueOf(5f));
        lf.add(Float.valueOf(3f));
        String[] pl = NetworkMessage.convertParams(lf);
        Assert.assertEquals(1, pl.length);
        NetworkMessage m = new NetworkMessageMock(pl);
        Assert.assertEquals(lf, m.getFloatList());
    }

}
