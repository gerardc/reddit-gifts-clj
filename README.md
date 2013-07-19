# Reddit Gifts challenge

This is a programming challenge that I saved last year but only recently got
around to tackling... in Clojure >:)

The challenge is descibed in the `challenge.md` file at the top level of the
project, it has an accompanying `sample_data.csv` file which I've also included.

## Getting started

The project is comprised of two leiningen projects. Once for generating data for
matching - `gifts-data` and the other for running the matching algorithm -
`gifts-matcher`.

- Generate data by running: `lein run ../sample_data.csv ../data.csv` from the
  `gifts-data` directory
- Run the matcher by running `lein run ../data.csv ../output.csv` from the
  `gifts-matcher` directory

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
