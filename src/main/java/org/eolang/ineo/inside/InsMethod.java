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
 * Inside with "method" as item.
 * @since 0.0.1
 */
public final class InsMethod implements Inside {
    /**
     * Origin object name.
     */
    private final String origin;

    /**
     * Method.
     */
    private final String method;

    /**
     * Ctor.
     * @param obj Object name
     * @param mtd Method name
     */
    public InsMethod(final String obj, final String mtd) {
        this.origin = obj;
        this.method = mtd;
    }

    @Override
    public String object() {
        return this.origin;
    }

    @Override
    public String item() {
        return this.method;
    }

    @Override
    public String replacement() {
        return String.format(
            "this-%s-%s",
            this.object(),
            this.item()
        );
    }
}
