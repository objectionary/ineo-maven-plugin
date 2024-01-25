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
import com.jcabi.xml.XSL;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;

/**
 * StaticizeMojo.
 * Replace recursive instance method call with static one.
 * @since 0.2
 */
@Mojo(
    name = "staticize",
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    threadSafe = true
)
public final class StaticizeMojo extends AbstractMojo {
    /**
     * Transformation.
     */
    private static final XSL TRANSFORMATION = new XSLDocumentOf(
        new TextOf(
            new ResourceOf("org/eolang/ineo/staticize/staticize.xsl")
        )
    );

    /**
     * STATICIZED XMIR.
     */
    private static final XML STATICIZED = new XMLDocumentOf(
        new TextOf(
            new ResourceOf("org/eolang/ineo/staticize/StaticizedA.xmir")
        )
    );

    /**
     * Sources directory.
     * @checkstyle MemberNameCheck (10 lines)
     */
    @Parameter(
        property = "ineo.staticizeSourcesDir",
        required = true,
        defaultValue = "${project.build.directory}/generated-sources/xmir"
    )
    private File sourcesDir;

    /**
     * Output directory.
     * @checkstyle MemberNameCheck (10 lines)
     */
    @Parameter(
        property = "ineo.staticizeOutputDir",
        required = true,
        defaultValue = "${project.build.directory}/generated-sources/staticized-xmir"
    )
    private File outputDir;

    @Override
    public void execute() {
        Logger.info(this, "Processing files in %s", this.sourcesDir);
        for (final Path file : new FilesOf(this.sourcesDir)) {
            Logger.info(this, "Processing %s", file);
            final XML before = new XMLDocumentOf(file);
            final XML after = StaticizeMojo.TRANSFORMATION.transform(before);
            final Path path = this.outputDir.toPath().resolve(
                this.sourcesDir.toPath().relativize(file)
            );
            if (before.equals(after)) {
                try {
                    new Saved(before, path).value();
                } catch (final IOException ex) {
                    throw new IllegalStateException(
                        String.format("Couldn't rewrite XMIR to output directory: %s", path),
                        ex
                    );
                }
            } else {
                Logger.info(this, "Found staticize optimization in %s", file.getFileName());
                try {
                    final String pckg = this.sourcesDir.toPath().relativize(file).toString()
                        .replace(String.format("%s%s", File.separator, file.getFileName()), "")
                        .replace(File.separator, ".");
                    final Path generated = this.outputDir.toPath().resolve(
                        String.join(
                            File.separator, pckg.replace(".", File.separator), "StaticizedA.xmir"
                        )
                    );
                    new Home(
                        new Saved(after, path),
                        new Saved(
                            StaticizeMojo.STATICIZED,
                            generated
                        )
                    ).save();
                    Logger.info(this, "New XMIR was generated: %s", generated);
                } catch (final IOException ex) {
                    throw new IllegalStateException("Couldn't save XMIR after transformation", ex);
                }
            }
        }
        Logger.info(this, "Staticized %d files", 1);
    }
}
