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

import java.io.File;
import java.nio.file.Path;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

/**
 * Text from XMIR.
 * @since 0.0.1
 */
public final class TextOfXmir implements Text {
    /**
     * Text.
     */
    private final Unchecked<UncheckedText> text;

    /**
     * Ctor.
     * @param sources Sources directory
     * @param name File name without extension
     */
    public TextOfXmir(final File sources, final String name) {
        this(sources.toPath(), name);
    }

    /**
     * Ctor.
     * @param sources Sources directory
     * @param name File name without extension
     */
    public TextOfXmir(final Path sources, final String name) {
        this(new XmirPath(sources, name));
    }

    /**
     * Ctor.
     * @param xmir Xmir
     */
    private TextOfXmir(final Scalar<Path> xmir) {
        this.text = new Unchecked<>(
            () -> new UncheckedText(
                new TextOf(xmir.value())
            )
        );
    }

    @Override
    public String asString() {
        return this.text.value().asString();
    }
}
