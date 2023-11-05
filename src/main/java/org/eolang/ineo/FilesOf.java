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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.cactoos.collection.CollectionEnvelope;

/**
 * Collection of files from the directory.
 * @since 0.0.1
 */
public final class FilesOf extends CollectionEnvelope<Path> {
    /**
     * Ctor.
     * @param file File to read files from.
     */
    public FilesOf(final File file) {
        this(file.toPath());
    }

    /**
     * Ctor.
     * @param dir Directory to read files from.
     */
    public FilesOf(final Path dir) {
        super(FilesOf.dir(dir));
    }

    /**
     * Get files from given directory.
     * @param dir Directory to get files from
     * @return Collection of files from directory
     */
    private static Collection<Path> dir(final Path dir) {
        try (Stream<Path> walk = Files.walk(dir)) {
            return walk.filter(file -> file.toFile().isFile()).collect(Collectors.toList());
        } catch (final IOException ex) {
            throw new IllegalStateException(
                String.format("Can't read files in %s folder during a walk", dir),
                ex
            );
        }
    }
}
