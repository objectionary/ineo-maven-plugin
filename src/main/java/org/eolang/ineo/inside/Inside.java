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

/**
 * Inside is what object "method" consists of in terms of inlining optimizations.
 * There are 3 parts: object (name) itself, item - object method or attribute
 * that is used, replacement - component that should replace item after optimization
 * Consider the next example:
 * <code><pre>
 * [d] > a
 *   cage d > this-d
 *   [] > foo
 *     this-d.plus 1 > @
 * </pre></code>
 * The target is "method" foo and its "inside" is: object "a", item is object "this-d",
 * the replacement will be "this-a-d".
 * After inlining object "a" into "b" the code will look like:
 * <code><pre>
 * [a-d] > b
 *   cage a-d > this-a-d
 *   [] > this-a-foo
 *     this-a-d.plus 1 > @
 *   [] > bar
 *     this-a-foo.plus 2 > @
 * </pre></code>
 * @since 0.0.1
 */
public interface Inside {
    /**
     * Object part of the inside.
     * @return Name of the object.
     */
    String object();

    /**
     * Item part of the inside.
     * @return Name of the item (function/attribute)
     */
    String item();

    /**
     * Replacement part of the inside.
     * @return String to put in the result inside.
     */
    String replacement();
}
