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
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;
import org.eolang.ineo.Home;
import org.eolang.ineo.InlinedInPlace;
import org.eolang.ineo.Saved;
import org.eolang.ineo.TextOfXmir;
import org.eolang.ineo.XMLDocumentOf;
import org.eolang.ineo.XSLDocumentOf;
import org.eolang.ineo.XmirPath;
import org.eolang.ineo.scenario.ScInPlace;
import org.eolang.ineo.transformation.Transformation;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test cases for {@link OpInPlace}.
 * @since 0.0.1
 * @todo #12:30min Enable {@link OpInPlaceTest#replacesWithComplexNames(Path)} test. The test fails
 *  with FileNotFound exception because {@link XmirPath} is not able to build the right path with
 *  name that contains dots like "org.eolang.main". Need teach XmirPath to do it and enable the
 *  test. Don't forget to remove the puzzle.
 */
@SuppressWarnings("PMD.TooManyMethods")
final class OpInPlaceTest {
    @Test
    void returnsTransformation(@TempDir final Path temp) throws Exception {
        OpInPlaceTest.copySources(temp);
        MatcherAssert.assertThat(
            String.format(
                "In place optimization should have returned object %s, but it didn't",
                Transformation.class
            ),
            OpInPlaceTest.optimize(temp),
            Matchers.instanceOf(Transformation.class)
        );
    }

    @Test
    void savesFileWithInlinedObject(@TempDir final Path temp) throws Exception {
        OpInPlaceTest.copySources(temp);
        OpInPlaceTest.optimize(temp);
        final Path inlined = new XmirPath(temp, new InlinedInPlace("b")).value();
        MatcherAssert.assertThat(
            String.format(
                "\"In place\" optimization should have saved file with name %s, but it didn't",
                inlined.getFileName()
            ),
            Files.exists(inlined),
            Matchers.is(true)
        );
    }

    @Test
    void copiesAndRenamesAttributesAndMethods(@TempDir final Path temp) throws Exception {
        OpInPlaceTest.copySources(temp);
        OpInPlaceTest.optimize(temp);
        final XML inlined = new XMLDocumentOf(
            new XmirPath(temp, new InlinedInPlace("b"))
        );
        MatcherAssert.assertThat(
            "Inlined object should have contained renamed free attribute from child object, but it didn't",
            inlined.nodes("//o[@name='a-d']"),
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            "Inlined object should have contained renamed \"cage\" with renamed free attribute, but it didn't",
            inlined.nodes("//o[@base='cage' and @name='this-a-d']/o[@base='a-d']"),
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            "Inlined object should have contained renamed method from child object, but it didn't",
            inlined.nodes("//o[@abstract and @name='this-a-foo']"),
            Matchers.hasSize(1)
        );
    }

    @Test
    void copiesOwnAttributesAndMethods(@TempDir final Path temp) throws Exception {
        OpInPlaceTest.copySources(temp);
        OpInPlaceTest.optimize(temp);
        final XML inlined = new XMLDocumentOf(
            new XmirPath(temp, new InlinedInPlace("b"))
        );
        MatcherAssert.assertThat(
            "Inlined object should have contained own free attribute, but it didn't",
            inlined.nodes("//o[@name='z']"),
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            "Inlined object should have contained own \"cage\" object with, but it didn't",
            inlined.nodes("//o[@base='cage' and @name='this-z']/o[@base='z']"),
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            "Inlined object should have contained own constructor, but it didn't",
            inlined.nodes("//o[@abstract and not(@base) and @name='new']"),
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            "Inlined object should have contained own method, but it didn't",
            inlined.nodes("//o[@abstract and @name='bar' and @own]"),
            Matchers.hasSize(1)
        );
    }

    @Test
    void replacesAttributesInCopiedMethods(@TempDir final Path temp) throws Exception {
        OpInPlaceTest.copySources(temp);
        OpInPlaceTest.optimize(temp);
        MatcherAssert.assertThat(
            "Copied method in inlined object should have used replaced renamed attribute, but it didn't",
            new XMLDocumentOf(
                new XmirPath(temp, new InlinedInPlace("b"))
            ).nodes("//o[@abstract and @name='this-a-foo']/o/o[@base='this-a-d']"),
            Matchers.hasSize(1)
        );
    }

    @Test
    void replacesOuterMethodsInInnerOnes(@TempDir final Path temp) throws Exception {
        OpInPlaceTest.copySources(temp);
        OpInPlaceTest.optimize(temp);
        MatcherAssert.assertThat(
            "Own method in inlined object should have used copied method, but it didn't",
            new XMLDocumentOf(
                new XmirPath(temp, new InlinedInPlace("b"))
            ).nodes("//o[@abstract and @name='bar']/o/o[@base='this-a-foo']"),
            Matchers.hasSize(1)
        );
    }

    @Test
    void replacesInMainXmir(@TempDir final Path temp) throws Exception {
        OpInPlaceTest.copySources(temp);
        MatcherAssert.assertThat(
            "Creation of object \"b\" in main.xmir should be replaced with \"b-inlined\", but it wasn't",
            new XSLDocumentOf(OpInPlaceTest.optimize(temp)).transform(
                new XMLDocumentOf(
                    new TextOf(
                        new XmirPath(temp, "main").value()
                    )
                )
            ).nodes(
                "//o[@base='.bar']/o[@base='.new']/o[@base='b-inlined' and count(o[@base='int'])=2]"
            ),
            Matchers.hasSize(1)
        );
    }

    @Test
    @Disabled
    void replacesWithComplexNames(@TempDir final Path temp) throws Exception {
        OpInPlaceTest.copySources(temp);
        Assertions.assertDoesNotThrow(
            () -> OpInPlaceTest.optimize(temp, "complex"),
            "Optimization of complex example should not throw an exception"
        );
    }

    /**
     * Copy sources to the given directory.
     * @param dir Directory to copy to
     * @throws Exception If fail to copy
     */
    private static void copySources(final Path dir) throws Exception {
        final String resource = "org/eolang/ineo/in_place/%s.xmir";
        new Home(
            new Saved(
                new TextOf(new ResourceOf(String.format(resource, "main"))),
                new XmirPath(dir, "main")
            ),
            new Saved(
                new TextOf(new ResourceOf(String.format(resource, "a"))),
                new XmirPath(dir, "a")
            ),
            new Saved(
                new TextOf(new ResourceOf(String.format(resource, "b"))),
                new XmirPath(dir, "b")
            ),
            new Saved(
                new TextOf(new ResourceOf(String.format(resource, "complex"))),
                new XmirPath(dir, "complex")
            )
        ).save();
    }

    /**
     * Apply "in-place" optimization.
     * @param dir Directory to get sources from
     * @return Result of optimization
     * @throws Exception If fails to optimize
     */
    private static Transformation optimize(final Path dir) throws Exception {
        return OpInPlaceTest.optimize(dir, "main");
    }

    /**
     * Apply "in-place" optimization.
     * @param dir Directory to get sources from
     * @param name Name of XMIR object
     * @return Result of optimization
     * @throws Exception If fails to optimize
     */
    private static Transformation optimize(final Path dir, final String name) throws Exception {
        return new OpInPlace(dir).apply(
            new ScInPlace().apply(
                new XMLDocumentOf(
                    new TextOfXmir(dir, name)
                )
            ).get(0)
        );
    }
}
