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
import com.jcabi.xml.XMLDocument;
import java.nio.file.Path;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.TextOf;
import org.w3c.dom.Node;

/**
 * XML document of.
 * @since 0.0.1
 * @checkstyle AbbreviationAsWordInNameCheck (10 lines)
 */
public final class XMLDocumentOf implements XML {
    /**
     * Document.
     */
    private final Unchecked<XMLDocument> document;

    /**
     * Ctor.
     * @param path Path to get file from
     */
    public XMLDocumentOf(final Path path) {
        this(new TextOf(path));
    }

    /**
     * Ctor.
     * @param path Path to the file
     */
    public XMLDocumentOf(final Scalar<Path> path) {
        this(() -> new TextOf(path.value()).asString());
    }

    /**
     * Ctor.
     * @param text Text.
     */
    public XMLDocumentOf(final Text text) {
        this.document = new Unchecked<>(
            new Sticky<>(() -> new XMLDocument(text.asString()))
        );
    }

    @Override
    public List<String> xpath(final String xpath) {
        return this.document.value().xpath(xpath);
    }

    @Override
    public List<XML> nodes(final String xpath) {
        return this.document.value().nodes(xpath);
    }

    @Override
    public XML registerNs(final String str, final Object obj) {
        return this.document.value().registerNs(str, obj);
    }

    @Override
    public XML merge(final NamespaceContext context) {
        return this.document.value().merge(context);
    }

    @Override
    public Node node() {
        return this.document.value().node();
    }

    @Override
    public boolean equals(final Object obj) {
        return this.document.value().equals(obj);
    }

    @Override
    public int hashCode() {
        return this.document.value().hashCode();
    }
}
