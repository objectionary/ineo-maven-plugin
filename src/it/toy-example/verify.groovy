/**
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

import groovy.xml.XmlParser

[
  "target/transpiled-sources/a.xmir",
  "target/transpiled-sources/b.xmir",
  "target/transpiled-sources/inlined-b.xmir",
  "target/transpiled-sources/main.xmir",
].each { assert new File(basedir, it).exists() }

def program = new XmlParser().parseText(
  new File(basedir, "target/transpiled-sources/main.xmir").text
)

[
  program.objects.o.size() == 1,
  program.objects.o[0].'@name' == 'main',
  program.objects.o[0].o[0].'@name' == 'main',
  program.objects.o[0].o[0].o[0].'@name' == 'args',
  program.objects.o[0].o[0].o[1].'@name' == '@',
  program.objects.o[0].o[0].o[1].'@base' == '.bar',
  program.objects.o[0].o[0].o[1].o[0].'@base' == '.new',
  program.objects.o[0].o[0].o[1].o[0].o[0].'@base' == 'inlined-b',
  program.objects.o[0].o[0].o[1].o[0].o[0].o.size() == 2,
  program.objects.o[0].o[0].o[1].o[0].o[0].o[0].'@base' == 'int',
  program.objects.o[0].o[0].o[1].o[0].o[0].o[1].'@base' == 'int',
].each { it -> assert it }

program = new XmlParser().parseText(
  new File(basedir, "target/transpiled-sources/inlined-b.xmir").text
)

[
  program.objects.o[0].'@name' == 'inlined-b',
  program.objects.o[0].o[0].'@name' == 'a-d',
  program.objects.o[0].o[1].'@name' == 'z',
  program.objects.o[0].o[2].'@name' == 'this-a-d',
  program.objects.o[0].o[2].'@base' == 'cage',
  program.objects.o[0].o[2].o[0].'@base' == 'a-d',
  program.objects.o[0].o[3].'@name' == 'this-z',
  program.objects.o[0].o[3].'@base' == 'cage',
  program.objects.o[0].o[3].o[0].'@base' == 'z',
  program.objects.o[0].o[4].'@abstract' == '',
  program.objects.o[0].o[4].'@name' == 'new',
  program.objects.o[0].o[4].o[0].'@base' == '^',
  program.objects.o[0].o[4].o[0].'@name' == '@',
  program.objects.o[0].o[5].'@abstract' == '',
  program.objects.o[0].o[5].'@name' == 'this-a-foo',
  program.objects.o[0].o[5].o[0].o[0].'@base' == 'this-a-d',
  program.objects.o[0].o[5].o[0].o[1].'@base' == 'int',
  program.objects.o[0].o[6].'@name' == 'bar',
  program.objects.o[0].o[6].o[0].o[0].'@base' == 'this-a-foo',
  program.objects.o[0].o[6].o[0].o[1].'@base' == 'int',
].each { it -> assert it }

true
