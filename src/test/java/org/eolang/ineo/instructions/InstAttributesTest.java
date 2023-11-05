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

package org.eolang.ineo.instructions;

import java.util.List;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.xembly.Directives;

/**
 * Test cases for {@link InstAttributes}.
 * @since 0.0.1
 */
final class InstAttributesTest {
    @Test
    void addsAttributeSuccessfully() {
        final String attribute = "hello";
        MatcherAssert.assertThat(
            String.format(
                "Instructions for attributes should have added object <o> with name %s, but they didn't",
                attribute
            ),
            new InstAttributes().add(attribute).toString(),
            Matchers.equalTo(
                InstAttributesTest.withAttribute(new Directives(), attribute).toString()
            )
        );
    }

    @Test
    void createsValidInstructionsFromList() {
        final String first = "first";
        final String second = "second";
        final List<String> attrs = new ListOf<>(first, second);
        MatcherAssert.assertThat(
            String.format(
                "Instructions for attributes from list %s should contain two objects <o> with corresponding names, but they didn't",
                attrs
            ),
            new InstAttributes(attrs).toString(),
            Matchers.equalTo(
                InstAttributesTest.withAttribute(
                    InstAttributesTest.withAttribute(
                        new Directives(),
                        first
                    ),
                    second
                ).toString()
            )
        );
    }

    private static Directives withAttribute(final Directives dirs, final String attribute) {
        return dirs.add("o").attr("name", attribute).up();
    }
}
