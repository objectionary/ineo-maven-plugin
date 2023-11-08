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
package org.eolang.ineo.optimization;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import org.cactoos.Text;
import org.cactoos.list.ListOf;
import org.cactoos.text.Sticky;
import org.eolang.ineo.InlinedInPlace;
import org.eolang.ineo.Saved;
import org.eolang.ineo.TextOfXmir;
import org.eolang.ineo.XMLDocumentOf;
import org.eolang.ineo.XSLDocumentOf;
import org.eolang.ineo.XmirObject;
import org.eolang.ineo.XmirPath;
import org.eolang.ineo.inside.InsAttribute;
import org.eolang.ineo.inside.InsMethod;
import org.eolang.ineo.inside.Inside;
import org.eolang.ineo.instructions.InstAttributes;
import org.eolang.ineo.instructions.InstCages;
import org.eolang.ineo.instructions.InstMethods;
import org.eolang.ineo.instructions.InstObject;
import org.eolang.ineo.instructions.InstOwnMethods;
import org.eolang.ineo.instructions.InstReplacedAttributes;
import org.eolang.ineo.instructions.InstReplacedMethods;
import org.eolang.ineo.instructions.Instructions;
import org.eolang.ineo.transformation.TfInPlaceInner;
import org.eolang.ineo.transformation.TfInPlaceOuter;
import org.eolang.ineo.transformation.Transformation;
import org.xembly.Directive;
import org.xembly.Xembler;

/**
 * Optimization for {@link org.eolang.ineo.scenario.ScInPlace}.
 * @since 0.0.1
 */
public final class OpInPlace implements Optimization {
    /**
     * Sources directory.
     */
    private final Path sources;

    /**
     * Ctor.
     * @param src Sources directory
     */
    public OpInPlace(final File src) {
        this(src.toPath());
    }

    /**
     * Ctor.
     * @param src Sources directory
     */
    public OpInPlace(final Path src) {
        this.sources = src;
    }

    @Override
    public Transformation apply(final XML node) throws Exception {
        final String base = node.xpath("@base").get(0);
        final Text inlined = new Sticky(new InlinedInPlace(base));
        final XmirObject outer = new XmirObject(
            new XMLDocumentOf(
                new TextOfXmir(this.sources, base)
            )
        );
        final List<XML> arguments = node.nodes("o");
        final List<XML> attributes = outer.attributes();
        final List<String> attrs = new ListOf<>();
        final Instructions<Iterable<Directive>> mtds = new InstObject();
        final List<Inside> inmethods = new ListOf<>();
        final List<XML> args = new ListOf<>();
        for (int idx = 0; idx < arguments.size(); ++idx) {
            final XML argument = arguments.get(idx);
            if (argument.xpath("@base").get(0).equals(".new")) {
                final XML arg = argument.nodes("o").get(0);
                final String inlinee = arg.xpath("@base").get(0);
                args.addAll(arg.nodes("o"));
                final XmirObject inner = new XmirObject(
                    new XMLDocumentOf(
                        new TextOfXmir(this.sources, inlinee)
                    )
                );
                final Instructions<XML> methods = new InstMethods();
                for (final XML method : inner.methods()) {
                    methods.add(method);
                    inmethods.add(new InsMethod(inlinee, method.xpath("@name").get(0)));
                }
                final List<Inside> inattrs = new ListOf<>();
                for (final XML attribute : inner.attributes()) {
                    final String name = attribute.xpath("@name").get(0);
                    attrs.add(String.join("-", inlinee, name));
                    inattrs.add(new InsAttribute(inlinee, name));
                }
                mtds.add(
                    new InstReplacedMethods<>(
                        new InstMethods(
                            new InstReplacedAttributes<>(methods, inattrs)
                        ), inmethods
                    )
                );
            } else {
                args.add(argument);
                attrs.add(attributes.get(idx).xpath("@name").get(0));
            }
        }
        new Saved(
            new XSLDocumentOf(
                new TfInPlaceInner(inmethods)
            ).transform(
                new XMLDocument(
                    new Xembler(
                        new InstObject()
                            .start(inlined)
                            .add(new InstAttributes(attrs))
                            .add(new InstCages(attrs))
                            .add(new InstOwnMethods(outer.constructor()))
                            .add(mtds)
                            .add(new InstOwnMethods(outer.methods()))
                    ).domQuietly()
                )
            ),
            new XmirPath(this.sources, inlined)
        ).value();
        return new TfInPlaceOuter(base, args);
    }
}
