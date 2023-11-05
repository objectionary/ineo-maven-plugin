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

import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;
import org.xembly.Directive;

/**
 * Get instruction from function.
 * @param <T> Type of instructions
 * @since 0.0.1
 */
public final class InstLambda<T> implements Instructions<T> {
    /**
     * Function to get instructions from.
     */
    private final Unchecked<Instructions<T>> function;

    /**
     * Ctor.
     * @param func Function
     */
    public InstLambda(final Scalar<Instructions<T>> func) {
        this.function = new Unchecked<>(new Sticky<>(func));
    }

    @Override
    public Instructions<T> add(final T value) {
        return this.function.value().add(value);
    }

    @Override
    public Iterator<Directive> iterator() {
        return this.function.value().iterator();
    }

    @Override
    public String toString() {
        return this.function.value().toString();
    }
}
