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

package org.eolang.ineo.scenario;

import com.jcabi.xml.XML;
import java.util.List;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;
import org.eolang.ineo.XMLDocumentOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link ScInPlace}.
 * @since 0.0.1
 */
final class ScInPlaceTest {
    @Test
    void findsInPlaceCorrectly() {
        final List<XML> nodes = new ScInPlace().apply(
            new XMLDocumentOf(
                new TextOf(
                    new ResourceOf(
                        "org/eolang/ineo/in_place/main.xmir"
                    )
                )
            )
        );
        MatcherAssert.assertThat(
            "List of \"in place\" scenarios should have one node, but it didn't",
            nodes,
            Matchers.hasSize(1)
        );
        final String base = "b";
        MatcherAssert.assertThat(
            String.format(
            "Base of the first element in \"in-place\" scenario should equal to \"%s\", but it didn't",
                base
            ),
            nodes.get(0).xpath("@base").get(0),
            Matchers.equalTo(base)
        );
    }
}
