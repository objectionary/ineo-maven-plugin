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
import java.io.IOException;
import java.nio.file.Path;
import org.cactoos.Func;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.io.InputOf;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;
import org.cactoos.scalar.IoChecked;
import org.cactoos.scalar.LengthOf;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.TextOfScalar;

/**
 * Saved.
 * @since 0.0.1
 */
public final class Saved {
    /**
     * Scalar.
     */
    private final Func<Path, Long> origin;

    /**
     * Path to save to.
     */
    private final Unchecked<Path> path;

    /**
     * Ctor.
     * @param xml XML to save
     * @param target Path to save to
     */
    public Saved(final XML xml, final Scalar<Path> target) {
        this(new InputOf(xml::toString), target);
    }

    /**
     * Ctor.
     * @param text Text to save
     * @param target Path to save to
     */
    public Saved(final Text text, final Scalar<Path> target) {
        this(new InputOf(text), target);
    }

    /**
     * Ctor.
     * @param input Input as string scalar to save
     * @param target Path to save to
     */
    public Saved(final Scalar<? extends String> input, final Path target) {
        this(input, () -> target);
    }

    /**
     * Ctor.
     * @param input Input to save
     * @param target Path to save to
     */
    public Saved(final Scalar<? extends String> input, final Scalar<Path> target) {
        this(new InputOf(new TextOfScalar(input)), target);
    }

    /**
     * Ctor.
     * @param input Input to save
     * @param target Path to save to
     */
    public Saved(final Input input, final Scalar<Path> target) {
        this(
            pth -> new LengthOf(
                new TeeInput(
                    input,
                    new OutputTo(pth)
                )
            ).value(),
            target
        );
    }

    /**
     * Ctor.
     * @param func Saving function
     * @param pth Path to save to
     */
    private Saved(final Func<Path, Long> func, final Scalar<Path> pth) {
        this.origin = func;
        this.path = new Unchecked<>(pth);
    }

    /**
     * Save.
     * @return Amount of saved bytes
     * @throws IOException If fails to save
     */
    public Long value() throws IOException {
        try {
            return new IoChecked<>(
                () -> this.origin.apply(
                    this.path.value()
                )
            ).value();
        } catch (final IOException ex) {
            throw new IOException(
                String.format(
                    "Failed while trying to save to %s",
                    this.path.value()
                ), ex
            );
        }
    }
}
