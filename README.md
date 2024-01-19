<img alt="logo" src="https://www.objectionary.com/cactus.svg" height="100px" />

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/objectionary/eo)](http://www.rultor.com/p/objectionary/eo)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)


[![Maven Central](https://img.shields.io/maven-central/v/org.eolang/ineo-maven-plugin.svg)](https://maven-badges.herokuapp.com/maven-central/org.eolang/ineo-maven-plugin)
[![Javadoc](http://www.javadoc.io/badge/org.eolang/ineo-maven-plugin.svg)](http://www.javadoc.io/doc/org.eolang/ineo-maven-plugin)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE.txt)
[![Hits-of-Code](https://hitsofcode.com/github/objectionary/ineo-maven-plugin?branch=master&label=Hits-of-Code)](https://hitsofcode.com/github/objectionary/ineo-maven-plugin/view?branch=master&label=Hits-of-Code)
![Lines of code](https://sloc.xyz/github/objectionary/ineo-maven-plugin)
[![codecov](https://codecov.io/gh/objectionary/ineo-maven-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/objectionary/ineo-maven-plugin)


**INEO** (stands for EO Inliner) is the tool to inline EO objects in order to get a faster and 
lighter EO code. 

# How to use

To run the plugin you need at least Maven 3.1.+ and Java 11+.
The plugin provides single `fuse` optimization that scans the directory and checks if there's any
`.xmir` file where the next code occurs:

```xml
<o base=".new">
  <o base="B"/>
  <o base=".new">
    <o base="A"/>
    <o base="int" data="bytes">
      <!-- BYTES HERE -->
    </o>
  </o>
</o>
```

The plugin adds new fused `BA.xmir` file into the directory and replaces the `xmir` above with the 
next one:

```xml

<o base=".new">
  <o base="BA"/>
  <o base="int" data="bytes">
    <!-- BYTES HERE -->
  </o>
</o>
```

Objects `A` and `B` must be from the same package and have the same prefix.

[Here](https://github.com/objectionary/benchmark) you may find a working example that uses the 
plugin with other optimization tools, like [JEO](https://github.com/objectionary/jeo-maven-plugin) 
and [OPEO](https://github.com/objectionary/opeo-maven-plugin)

## Invoke the plugin from the Maven lifecycle

You can run the plugin from the Maven lifecycle by adding the following
configuration to your `pom.xml` file:

```xml

<build>
  <plugins>
    <plugin>
      <groupId>org.eolang</groupId>
      <artifactId>ineo-maven-plugin</artifactId>
      <version>0.1.2</version>
      <executions>
        <execution>
          <id>fuse</id>
          <phase>generate-sources</phase>
          <goals>
            <goal>fuse</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

More details about plugin usage you can find in our
[Maven site](https://objectionary.github.io/ineo-maven-plugin).

## How to Contribute

Fork repository, make changes, then send us
a [pull request](https://www.yegor256.com/2014/04/15/github-guidelines.html).
We will review your changes and apply them to the `master` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request please run full Maven build:

```bash
$ mvn clean install -Pqulice
```

You will need [Maven 3.3+](https://maven.apache.org) and Java 11+ installed.