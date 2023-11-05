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
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
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
        defaultValue = "{project.build.outputDirectory}/transpiled-sources"
    )
    private File sources;

    /**
     * Optimizations.
     * @checkstyle MemberNameCheck (5 lines)
     */
    private final Map<Scenario, Optimization> optimizations = new MapOf<>(
        new MapEntry<>(new ScInPlace(), new OpInPlace(this.sources))
    );

    @Override
    public void execute() {
        for (final Path path : new FilesOf(this.sources)) {
            Logger.info(this, "Scanning %s", path);
            final XML source = new XMLDocumentOf(new TextOf(path));
            for (final Map.Entry<Scenario, Optimization> optimization
                : this.optimizations.entrySet()) {
                final Scenario scenario = optimization.getKey();
                final List<XML> nodes = scenario.apply(source);
                if (!nodes.isEmpty()) {
                    Logger.info(this, "Scenario \"%s\" is found", scenario.toString());
                    for (final XML node : nodes) {
                        try {
                            new Saved(
                                new XSLDocumentOf(
                                    optimization.getValue().apply(node)
                                ).transform(source),
                                new XmirPath(this.sources, path.getFileName().toString())
                            ).value();
                        } catch (final Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
    }
}
