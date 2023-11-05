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
package org.eolang.ineo;

import com.jcabi.xml.XML;
import java.util.List;

/**
 * EO object in XMIR with access to attributes and methods.
 * @since 0.0.1
 */
public final class EoObject {
    /**
     * Xpath for attributes.
     */
    private static final String ATTRS = "//objects/o[1]/o[not(@base) and not(@abstract)]";

    /**
     * Xpath for methods.
     * @checkstyle LineLengthCheck (4 lines)
     */
    private static final String MTDS = "//objects/o[1]/o[not(@base) and @abstract and @name!='new']";

    /**
     * Xpath for constructor.
     */
    private static final String CTOR = "//objects/o[1]/o[not(@base) and @abstract and @name='new']";

    /**
     * XML document.
     */
    private final XML document;

    /**
     * Ctor.
     * @param doc XML document.
     */
    public EoObject(final XML doc) {
        this.document = doc;
    }

    /**
     * Get XML nodes with attributes of the object.
     * @return XML nodes with attributes
     */
    public List<XML> attributes() {
        return this.document.nodes(EoObject.ATTRS);
    }

    /**
     * Get XML nodes with methods of the object.
     * @return XML nodes with methods
     */
    public List<XML> methods() {
        return this.document.nodes(EoObject.MTDS);
    }

    /**
     * Get XML node with constructor of the object.
     * @return XML node with constructor
     */
    public XML constructor() {
        return this.document.nodes(EoObject.CTOR).get(0);
    }
}
