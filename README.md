This library seeks to provide wrappers for performant NLP tools that are written in rust in the JVM and JS ecosystem
The wrappers are generated with [diplomat](https://rust-diplomat.github.io/book/) and [diplomat-java](https://github.com/rust-diplomat/diplomat-java).

There are currently three wrapping libraries
- Kotlin
- Java
- JS

One may ask why we need two different wrappers for JVM languages, and although it's not necessary, the Java library
is made using [diplomat-java](https://github.com/rust-diplomat/diplomat-java) which leverages the [Panama Native 
Interface API](https://openjdk.org/projects/panama/).

The benefits of this are mostly significant speedups. This repository includes benchmarks. In kotlin we try multiple 
approaches to improve performance, but any ergonomic approach yields a 2-3x longer runtime. This seems to be mostly
overhead from converting native data into JVM data. By comparison the Panama backend only has a ~10% overhead. 
The Kotlin and Java benchmarks are written with jmh, and the rust benchmark uses criterion (hence the different 
reporting).
- Kotlin: 
  - BenchOffsetsVsStrings.benchOffsets: Provides a list of structures which contain the UTF16 offsets  
    - 1409634,952 ± 338410,783  ns/op
  - BenchOffsetsVsStrings.benchSpans: Provides an array of ints where each consecutive pair in the array is the 
  UTf16 offset of a token in the original string
    - 809420,919 ±  64266,160  ns/op
  - BenchOffsetsVsStrings.benchTokens: Just passes the tokenised strings
    - 1302513,816 ± 263704,908  ns/op
  - BenchOffsetsVsStrings.benchWhitespaceTokenize: A simple comparison with pure whitespace tokenisation
    - 1141,166 ±    289,416  ns/op
- Java: 
  - RustnlpBench.benchOpaque: A singe benchmark that extracts the tokens as strings
    - 644465,032          ns/op
- Rust
  - tokenize: Just the basic tokenisation
    - time:   \[585.25 µs 586.91 µs 588.65 µs\]
  - tokenize offsets: If we want to provide utf16 offsets, we have to calculate them
    - time:   \[765.59 µs 767.26 µs 768.91 µs\]
  - whitespace tokenize: Just for comparison with kotlin, but dang it's fast
    - time:   \[165.21 ns 165.43 ns 165.68 ns\]

