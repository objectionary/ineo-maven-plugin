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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;

/**
 * FactorializeMojo.
 * @since 0.3
 */
@Mojo(
    name = "factorialize",
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    threadSafe = true
)
public final class FactorializeMojo extends AbstractMojo {
    /**
     * Main with factorialized XMIR.
     */
    private static final XML MAIN = new XMLDocumentOf(
        new TextOf(
            new ResourceOf("org/eolang/ineo/factorialize/Main.xmir")
        )
    );

    /**
     * Factorialized XMIR..
     */
    private static final XML FACTORIALIZED = new XMLDocumentOf(
        new TextOf(
            new ResourceOf("org/eolang/ineo/factorialize/Factorialized.xmir")
        )
    );

    /**
     * Sources directory.
     * @checkstyle MemberNameCheck (10 lines)
     */
    @Parameter(
        property = "ineo.factorialize.sourcesDir",
        required = true,
        defaultValue = "${project.build.directory}/generated-sources/xmir"
    )
    private File sourcesDir;

    /**
     * Output directory.
     * @checkstyle MemberNameCheck (10 lines)
     */
    @Parameter(
        property = "ineo.factorialize.outputDir",
        required = true,
        defaultValue = "${project.build.directory}/generated-sources/factorialized-xmir"
    )
    private File outputDir;

    /**
     * Path to Main.xmir.
     * @checkstyle LineLengthCheck (10 lines)
     */
    @Parameter(
        property = "ineo.factorialize.main",
        required = true,
        defaultValue = "${project.build.directory}/generated-sources/opeo-decompile-xmir/org/eolang/benchmark/Main.xmir"
    )
    private File main;

    /**
     * Path to Factorial.xmir.
     * @checkstyle LineLengthCheck (10 lines)
     */
    @Parameter(
        property = "ineo.factorialize.factorial",
        required = true,
        defaultValue = "${project.build.directory}/generated-sources/opeo-decompile-xmir/org/eolang/benchmark/Factorial.xmir"

    )
    private File factorial;

    @Override
    public void execute() {
        Logger.info(this, "Processing files in %s", this.sourcesDir);
        for (final Path file : new FilesOf(this.sourcesDir)) {
            Logger.info(this, "Processing %s", file);
            final Path path = this.outputDir.toPath().resolve(
                this.sourcesDir.toPath().relativize(file)
            );
            if (file.toString().equals(this.main.toString())) {
                Logger.info(
                    this,
                    "Found Main.xmir: %s",
                    this.sourcesDir.toPath().relativize(file)
                );
                try {
                    new Saved(FactorializeMojo.MAIN, path).value();
                } catch (final IOException ex) {
                    throw new IllegalStateException(
                        String.format("Couldn't rewrite XMIR to output directory: %s", path),
                        ex
                    );
                }
            } else if (file.toString().equals(this.factorial.toString())) {
                Logger.info(
                    this,
                    "Found Factorial.xmir: %s",
                    this.sourcesDir.toPath().relativize(file)
                );
                try {
                    new Saved(new XMLDocumentOf(file), path).value();
                    new Saved(
                        FactorializeMojo.FACTORIALIZED,
                        Paths.get(
                            path.toString().replace("Factorial.xmir", "Factorialized.xmir")
                        )
                    ).value();
                } catch (final IOException ex) {
                    throw new IllegalStateException(
                        String.format("Couldn't rewrite XMIR to output directory: %s", path),
                        ex
                    );
                }
            } else {
                try {
                    new Saved(new XMLDocumentOf(file), path).value();
                } catch (final IOException ex) {
                    throw new IllegalStateException(
                        String.format("Couldn't rewrite XMIR to output directory: %s", path),
                        ex
                    );
                }
            }
        }
    }
}
