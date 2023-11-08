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

import com.jcabi.xml.Sources;
import com.jcabi.xml.XML;
import com.jcabi.xml.XSL;
import com.jcabi.xml.XSLDocument;
import org.cactoos.Text;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * XSL document of.
 * @since 0.0.1
 * @checkstyle AbbreviationAsWordInNameCheck (10 lines)
 */
public final class XSLDocumentOf implements XSL {
    /**
     * Original document.
     */
    private final Unchecked<XSL> document;

    /**
     * Ctor.
     * @param text Text
     */
    public XSLDocumentOf(final Text text) {
        this.document = new Unchecked<>(new Sticky<>(() -> new XSLDocument(text.asString())));
    }

    @Override
    public XML transform(final XML xml) {
        return this.document.value().transform(xml);
    }

    @Override
    public String applyTo(final XML xml) {
        return this.document.value().applyTo(xml);
    }

    @Override
    public XSL with(final Sources sources) {
        return this.document.value().with(sources);
    }

    @Override
    public XSL with(final String str, final Object obj) {
        return this.document.value().with(str, obj);
    }

    @Override
    public boolean equals(final Object obj) {
        return this.document.value().equals(obj);
    }

    @Override
    public int hashCode() {
        return this.document.value().hashCode();
    }

    @Override
    public String toString() {
        return this.document.value().toString();
    }
}
