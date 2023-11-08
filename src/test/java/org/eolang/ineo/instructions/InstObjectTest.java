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

import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.xembly.Directive;

/**
 * Test cases for {@link InstObject}.
 * @since 0.0.1
 */
final class InstObjectTest {
    @Test
    void constructsValidStartProgram() {
        final Instructions<Iterable<Directive>> program = new InstObject().start(
            new TextOf("test")
        );
        MatcherAssert.assertThat(
            "Start object instructions should have contained valid program XML, but they didn't",
            new NodesOfInstructions<>(
                program,
                "/program[objects[o[@abstract and @name='test']]]"
            ),
            Matchers.hasSize(1)
        );
    }

    @Test
    void addsOtherInstructionsSuccessfully() {
        MatcherAssert.assertThat(
            "Instructions for objects should have contained instructions for directives, but they didn't",
            new NodesOfInstructions<>(
                new InstObject().add(
                    new InstDirectives<>().add("hello")
                ),
                "/hello"
            ),
            Matchers.hasSize(1)
        );
    }
}
