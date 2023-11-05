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

import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.xembly.Directive;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;

/**
 * Test cases for {@link InstCages}.
 * @since 0.0.1
 */
final class InstCagesTest {
    /**
     * Formate for finding "CAGE" object.
     */
    private static final String CAGE = "//o[@base='cage' and @name='this-%s' and o[@base='%s']]";

    /**
     * Message for assertion.
     * @checkstyle LineLengthCheck (4 lines)
     */
    private static final String MESSAGE = "XML document from instructions for cages should have contained application of cage with %s argument and this-%s name, but it didn't";

    @Test
    void addsCageForAttributeSuccessfully() {
        final String name = "weight";
        MatcherAssert.assertThat(
            String.format(InstCagesTest.MESSAGE, name, name),
            new NodesOfInstructions<>(
                new InstCages().add(name),
                String.format(InstCagesTest.CAGE, name, name)
            ),
            Matchers.hasSize(1)
        );
    }

    @Test
    void createsSuccessfullyFromList() throws ImpossibleModificationException {
        final String first = "first";
        final String second = "second";
        final Instructions<Iterable<Directive>> cages = new InstObject(
            new Directives().add("objects")
        ).add(
            new InstCages(
                new ListOf<>(first, second)
            )
        );
        MatcherAssert.assertThat(
            String.format(InstCagesTest.MESSAGE, first, first),
            new NodesOfInstructions<>(
                cages,
                String.format(InstCagesTest.CAGE, first, first)
            ),
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            String.format(InstCagesTest.MESSAGE, second, second),
            new NodesOfInstructions<>(
                cages,
                String.format(InstCagesTest.CAGE, second, second)
            ),
            Matchers.hasSize(1)
        );
    }
}
