<?xml version="1.0" encoding="UTF-8"?>
<!--
The MIT License (MIT)

Copyright (c) 2016-2023 Objectionary.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" id="staticize" version="2.0">
  <xsl:output encoding="UTF-8" method="xml"/>
  <xsl:template match="//o[@base='.get' and @scope='descriptor=()I|interfaced=false|name=get|owner=org/eolang/benchmark/A|type=method']">
    <o base=".get" scope="descriptor=()I|interfaced=false|name=get|owner=org/eolang/benchmark/StaticizedA|type=method">
      <o base=".new" scope="descriptor=(I)V|interfaced=false">
        <o base="duplicated">
          <o base=".new-type">
            <o base="string" data="bytes">6F 72 67 2F 65 6F 6C 61 6E 67 2F 62 65 6E 63 68 6D 61 72 6B 2F 53 74 61 74 69 63 69 7A 65 64 41</o>
          </o>
        </o>
        <o base=".minus">
          <o base=".get-field">
            <o base=".d" scope="descriptor=I|name=d|owner=org/eolang/benchmark/StaticizedA|type=field">
              <o base="$" scope="descriptor=org.eolang.benchmark.StaticizedA"/>
            </o>
          </o>
          <o base="int" data="bytes">00 00 00 00 00 00 00 01</o>
        </o>
      </o>
    </o>
  </xsl:template>
  <!-- Ignore other elements -->
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
