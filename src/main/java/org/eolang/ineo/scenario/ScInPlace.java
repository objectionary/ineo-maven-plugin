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

/**
 * Scenario where object is created inside the creation of another object.
 * Example in Java would look like:
 * <code>
 * <pre>new B(new A(42))</pre>
 * </code>
 * In EO, it would look like:
 * <code>
 * <pre>(b (a 42).new).new</pre>
 * </code>
 * @since 0.0.1
 */
public final class ScInPlace extends ScWrap {
    /**
     * Xpath.
     */
    private static final String XPATH = "//o[@base='.new' and count(o[o[@base='.new']])>0]/o";

    /**
     * Ctor.
     */
    public ScInPlace() {
        super(new ScXpath(ScInPlace.XPATH));
    }

    @Override
    public String toString() {
        return "In place objects";
    }
}
