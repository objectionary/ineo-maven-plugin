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
import org.eolang.ineo.inside.Inside;

/**
 * Instructions with replaced methods using.
 * @param <T> Type of instructions
 * @since 0.0.1
 */
public final class InstReplacedMethods<T> extends InstWrap<T> {
    /**
     * Format for simple bases.
     */
    private static final String FORMAT_SIMPLE = "ATTR \"name\", \"%s\"";

    /**
     * Format for reversed bases.
     */
    private static final String FORMAT_REVERSED = "ATTR \"base\", \"\\.%s\"";

    /**
     * Ctor.
     * @param instructions Instructions
     * @param insides Insides
     */
    public InstReplacedMethods(
        final Instructions<T> instructions,
        final List<Inside> insides
    ) {
        super(
            new InstReplaced<>(
                new InstReplaced<>(
                    instructions,
                    insides,
                    inside -> String.format(InstReplacedMethods.FORMAT_SIMPLE, inside.item()),
                    inside -> String.format(
                        InstReplacedMethods.FORMAT_SIMPLE,
                        inside.replacement()
                    )
                ),
                insides,
                inside -> String.format(InstReplacedMethods.FORMAT_REVERSED, inside.item()),
                inside -> String.format(
                    InstReplacedMethods.FORMAT_REVERSED,
                    inside.replacement()
                )
            )
        );
    }
}
