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
package org.eolang.ineo.transformation;

import com.jcabi.xml.XML;
import java.util.List;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.Reduced;
import org.eolang.ineo.InlinedInPlace;

/**
 * Outer transformation for {@link org.eolang.ineo.optimization.OpInPlace} optimization.
 * @since 0.0.1
 */
public class TfInPlaceOuter extends TfWrap {
    /**
     * Template.
     */
    private static final String TEMPLATE = String.join(
        "\n",
        "  <xsl:template match=\"//o[@base='.new']/o[@base='%s' and o[@base='.new']][1]\">",
        "    <o base=\"%s\">",
        "      %s",
        "    </o>",
        "  </xsl:template>"
    );

    /**
     * Ctor.
     * @param object Object name to replace
     * @param arguments New arguments
     */
    public TfInPlaceOuter(final String object, final List<XML> arguments) {
        super(
            new TfTemplate(
                () -> String.format(
                    TfInPlaceOuter.TEMPLATE,
                    object,
                    new InlinedInPlace(object),
                    new Reduced<>(
                        new Mapped<>(
                            Object::toString,
                            arguments
                        ),
                        (acc, item) -> String.join("      ", acc, item)
                    ).value()
                )
            )
        );
    }
}
