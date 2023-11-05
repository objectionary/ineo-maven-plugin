/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2023 Objectionary.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.eolang.ineo.inside;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link InsMethod}.
 * @since 0.0.1
 */
final class InsMethodTest {
    @Test
    void returnsObjectItself() {
        final String object = "cat";
        final String result = new InsMethod(object, "voice").object();
        MatcherAssert.assertThat(
            String.format(
                "Expected %s object, but got %s",
                object, result
            ),
            result,
            Matchers.equalTo(object)
        );
    }

    @Test
    void returnsValidItem() {
        final String item = "voice";
        final String result = new InsMethod("cat", item).item();
        MatcherAssert.assertThat(
            String.format(
                "Expected %s item, but got %s",
                item, result
            ),
            result,
            Matchers.equalTo(item)
        );
    }

    @Test
    void returnsValidReplacement() {
        final String replacement = "this-cat-voice";
        final String result = new InsMethod("cat", "voice").replacement();
        MatcherAssert.assertThat(
            String.format(
                "Expected %s replacement, but got %s",
                result, replacement
            ),
            result,
            Matchers.equalTo(replacement)
        );
    }
}
