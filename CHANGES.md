What’s new in Glass
===================

Glass 0.6 — ????-??-??
----------------------

  * The `$` prefix can now be used for hexadecimal numbers.
  * Symbols can no longer start with the `$` character (bc).
  * The `>>>` unsigned shift right operator is now supported.

Glass 0.5 — 2017-01-18
----------------------

  * The `incbin` directive is now supported.
  * The ternary `?:` operator is now supported.
  * The `.` member operator is now an official operator.
  * Sequences can now be indexed with the `[]` operator.
  * Symbols can now also start with the `$` character.
  * Include now supports the `once` annotation.
  * The `0x` and `0X` prefix can now be used for hexadecimal numbers.
    Thanks to Paul Bosselaar.
  * Macro arguments can now specify default values with `=`.
  * Expressions can now span multiple lines.
  * The `org` statement no longer affects the address of a preceding label (bc).
  * Some invalid instructions now throw errors (e.g. `bit 7,ixh`).
  * Contexts are now resolved through macro arguments.
  * Instructions and macros can now be passed into macro arguments.
  * Section identifiers are now resolved like any other expression.
  * Examples for COM, ROM and BIN files are now included in the source code.
  * Error messages were improved.
  * Java 8 is now required.

Glass 0.4 — 2014-06-16
----------------------

  * The `#` and `%` prefix can now be used for hexadecimal and binary numbers.
  * A syntax error now occurs for number parsing errors.

Glass 0.3 — 2014-01-11
----------------------

  * Macro definition symbols can now also be referenced. The contents are
    assembled on address 0, effectively turning the inner symbols into offsets.
  * The individual (indefinite) repeat blocks can now be referenced.
  * Labels suffixed with a colon can now be indented.
  * Errors now contain more location information.
  * Section targets can now be reference before they are specified.
  * Division by zero errors are now handled better.
  * Fix bug when specifying macros with more than one argument.

Glass 0.2 — 2014-01-04
----------------------

  * Added a `section` directive to assemble code into groups.
  * A `virtual` annotation can be specified on `ds`.
  * Search includes relative to source path.
  * Support negating flags (`!z`).

Glass 0.1.1 — 2014-01-03
------------------------

  * Support register symbols.

Glass 0.1 — 2014-01-02
----------------------

  * Initial release.
