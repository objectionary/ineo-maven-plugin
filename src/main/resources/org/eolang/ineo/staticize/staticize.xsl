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
  <!--
  <o base=".get" scope="name=get|descriptor=()I|owner=org/eolang/benchmark/A|type=method">
     <o base=".new" scope="descriptor=(I)V">
        <o base="org/eolang/benchmark/A"/>
        <o base="int" data="bytes">00 00 00 00 00 00 00 2A</o>
     </o>
  </o>
  -->
  <xsl:template match="//o[@base='.get' and @scope and o[1][@base='.new' and o[1][@base='org/eolang/benchmark/A']]]">
    <o base=".get" scope="name=get|descriptor=()I|owner=org/eolang/benchmark/StaticizedA|type=method">
      <o base=".new" scope="descriptor=(I)V">
        <o base="org/eolang/benchmark/StaticizedA"/>
        <xsl:copy-of select="./o[position()=1]/o[position()&gt;1]"/>
      </o>
      <xsl:copy-of select="./o[position()&gt;1]"/>
    </o>
  </xsl:template>
  <!-- Ignore other elements -->
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
