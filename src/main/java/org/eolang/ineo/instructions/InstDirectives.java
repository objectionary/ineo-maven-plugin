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
import java.util.function.BiConsumer;
import org.xembly.Directive;
import org.xembly.Directives;

/**
 * Instructions with directives.
 * @param <T> Type of instructions to add
 * @since 0.0.1
 */
public final class InstDirectives<T> implements Instructions<T> {
    /**
     * Directives.
     */
    private final Directives directives;

    /**
     * Function for modifying directives.
     */
    private final BiConsumer<Directives, T> function;

    /**
     * Ctor.
     */
    public InstDirectives() {
        this(new Directives());
    }

    /**
     * Ctor.
     * @param directives Directives
     */
    public InstDirectives(final Directives directives) {
        this(directives, Directives::add);
    }

    /**
     * Ctor.
     * @param func Function for modifying directives
     */
    public InstDirectives(final BiConsumer<Directives, T> func) {
        this(new Directives(), func);
    }

    /**
     * Ctor.
     * @param dirs Directives
     * @param func Function for modifying directives
     */
    public InstDirectives(final Directives dirs, final BiConsumer<Directives, T> func) {
        this.directives = dirs;
        this.function = func;
    }

    @Override
    public Iterator<Directive> iterator() {
        return this.directives.iterator();
    }

    @Override
    public Instructions<T> add(final T value) {
        this.function.accept(this.directives, value);
        return this;
    }

    @Override
    public String toString() {
        return this.directives.toString();
    }
}
