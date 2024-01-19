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
import com.jcabi.xml.XMLDocument;
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
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

/**
 * Fuse {@code new B(new A(42).bar()} java code into {@code new BA(42).bar()}.
 * @since 0.1
 */
@Mojo(
    name = "fuse",
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    threadSafe = true
)
public final class FuseMojo extends AbstractMojo {
    /**
     * Transformation.
     */
    private static final XSL TRANSFORMATION = new XSLDocumentOf(
        new TextOf(
            new ResourceOf("org/eolang/ineo/fuse/fuse.xsl")
        )
    );

    /**
     * Fused XMIR.
     */
    private static final XML FUSED = new XMLDocumentOf(
        new TextOf(
            new ResourceOf("org/eolang/ineo/fuse/BA.xmir")
        )
    );

    /**
     * Sources directory.
     * @checkstyle MemberNameCheck (10 lines)
     */
    @Parameter(
        property = "ineo.sourcesDir",
        required = true,
        defaultValue = "${project.build.directory}/generated-sources/xmir"
    )
    private File sourcesDir;

    /**
     * Output directory.
     * @checkstyle MemberNameCheck (10 lines)
     */
    @Parameter(
        property = "ineo.outputDir",
        required = true,
        defaultValue = "${project.build.directory}/generated-sources/fused-xmir"
    )
    private File outputDir;

    @Override
    public void execute() {
        Logger.info(this, "Processing files in %s", this.sourcesDir);
        for (final Path file : new FilesOf(this.sourcesDir)) {
            Logger.info(this, "Processing %s", file);
            final XML before = new XMLDocumentOf(file);
            final XML after = FuseMojo.TRANSFORMATION.transform(before);
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
                Logger.info(this, "Found fuse optimization in %s", file.getFileName());
                try {
                    final String pckg = this.sourcesDir.toPath().relativize(file).toString()
                        .replace(String.format("%s%s", File.separator, file.getFileName()), "");
                    final Path generated = this.outputDir.toPath().resolve(
                        String.join(File.separator, pckg, "BA.xmir")
                    );
                    new Home(
                        new Saved(after, path),
                        new Saved(
                            new XMLDocument(
                                new Xembler(
                                    new Directives()
                                        .append(FuseMojo.FUSED.node())
                                        .xpath("//metas/meta[head/text()='package']/tail")
                                        .set(pckg)
                                        .xpath("//metas/meta[head/text()='package']/part")
                                        .set(pckg)
                                ).xml()
                            ),
                            generated
                        )
                    ).save();
                    Logger.info(this, "New XMIR was generated: %s", generated);
                } catch (final IOException ex) {
                    throw new IllegalStateException("Couldn't save XMIR after transformation", ex);
                } catch (final ImpossibleModificationException ex) {
                    throw new IllegalStateException("Couldn't transform fused XMIR", ex);
                }
            }
        }
        Logger.info(this, "Fused %d files", 1);
    }
}
