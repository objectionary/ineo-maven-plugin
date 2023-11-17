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

import java.nio.file.Path;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test cases for {@link XmirPath}.
 */
public class XmirPathTest {
    @Test
    void buildsValidPath(@TempDir final Path temp) throws Exception {
        MatcherAssert.assertThat(
            "XmirPath should have built valid path, but it didn't",
            new XmirPath(temp, "main").value(),
            Matchers.equalTo(
                temp.resolve("main.xmir").toString()
            )
        );
    }

    @Test
    @Disabled
    void replacesDotsInGivenPath(@TempDir final Path temp) throws Exception {
        MatcherAssert.assertThat(
            "XmirPath should have replaced dot's in name with slashes, but it didn't",
            new XmirPath(temp, "org.eolang.main").value(),
            Matchers.equalTo(
                temp.resolve("org")
                    .resolve("eolang")
                    .resolve("main.xmir")
                    .toString()
            )
        );
    }
}
