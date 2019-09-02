# Data Integration ETL example

The purpose of this project was to try to build an ETL
project from scratch, using plain Spring Boot 2. It does not use Spring Batch

The main effort is on the `core` package.

It is intended to hold basic utility/helper classes
that give support to the user to extract (and then transform/load) data from
three sources: XLSX, CSV and another JDBC connection (hence the need for a second
DB connection).
