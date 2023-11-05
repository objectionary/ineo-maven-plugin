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
import org.cactoos.Func;
import org.eolang.ineo.inside.Inside;
import org.xembly.Directives;

/**
 * Instructions replaced from the list of insides.
 * @param <T> Type of instructions
 * @since 0.0.1
 */
public final class InstReplaced<T> extends InstWrap<T> {
    /**
     * Ctor.
     * @param instructions Instructions
     * @param insides Insides
     * @param regex Regex function
     * @param replacement Replacement function
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public InstReplaced(
        final Instructions<T> instructions,
        final List<Inside> insides,
        final Func<Inside, String> regex,
        final Func<Inside, String> replacement
    ) {
        super(
            new InstLambda<>(
                () -> {
                    String result = instructions.toString();
                    for (final Inside inside : insides) {
                        result = result.replaceAll(
                            regex.apply(inside),
                            replacement.apply(inside)
                        );
                    }
                    return new InstDirectives<>(
                        new Directives(result)
                    );
                }
            )
        );
    }
}
