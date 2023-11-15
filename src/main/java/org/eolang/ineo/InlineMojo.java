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

import com.jcabi.log.Logger;
import com.jcabi.xml.XML;
import com.yegor256.xsline.StXSL;
import com.yegor256.xsline.TrDefault;
import com.yegor256.xsline.Xsline;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cactoos.Func;
import org.cactoos.iterable.Mapped;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.cactoos.text.TextOf;
import org.eolang.ineo.optimization.OpInPlace;
import org.eolang.ineo.optimization.Optimization;
import org.eolang.ineo.scenario.ScInPlace;
import org.eolang.ineo.scenario.Scenario;

/**
 * Inline mojo.
 * @since 0.0.1
 */
@Mojo(
    name = "inline",
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    threadSafe = true
)
public final class InlineMojo extends AbstractMojo {
    /**
     * Directory with .xmir sources to optimize.
     */
    @Parameter(
        property = "sources",
        required = true,
        defaultValue = "${project.basedir}/target/transpiled-sources"
    )
    private File sources;

    /**
     * Optimizations.
     * @checkstyle MemberNameCheck (5 lines)
     */
    private static final Map<Scenario, Func<File, Optimization>> OPTIMIZATIONS = new MapOf<>(
        new MapEntry<>(new ScInPlace(), OpInPlace::new)
    );

    @Override
    public void execute() throws MojoExecutionException {
        for (final Path path : new FilesOf(this.sources)) {
            Logger.info(this, "Scanning %s", path);
            XML before = new XMLDocumentOf(new TextOf(path));
            XML after;
            boolean more;
            boolean save = false;
            do {
                after = before;
                for (final Map.Entry<Scenario, Func<File, Optimization>> optimization
                    : InlineMojo.OPTIMIZATIONS.entrySet()) {
                    final Scenario scenario = optimization.getKey();
                    final List<XML> nodes = scenario.apply(before);
                    if (!nodes.isEmpty()) {
                        Logger.info(this, "Scenario \"%s\" is found", scenario.toString());
                        after = new Xsline(
                            new TrDefault<>(
                                new Mapped<>(
                                    node -> new StXSL(
                                        new XSLDocumentOf(
                                            optimization.getValue().apply(this.sources).apply(node)
                                        )
                                    ),
                                    nodes
                                )
                            )
                        ).pass(before);
                    }
                }
                more = !after.equals(before);
                if (more) {
                    save = true;
                    before = after;
                }
            } while (more);
            if (save) {
                try {
                    new Saved(after, path).value();
                } catch (final IOException ex) {
                    throw new MojoExecutionException(ex);
                }
            }
        }
    }
}
