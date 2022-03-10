What’s new in Glass
===================

Glass 0.6 — 2022-03-10
----------------------

  * The `$` prefix can now be used for hexadecimal numbers.
  * Symbols can no longer start with the `$` character (bc).
  * The `org` pseudo-op now supports values outside the 0-FFFFH range.
  * Labels are now associated with the next following mnemonic.
  * Arguments now look up in their instruction’s parent scope.
  * Comments can now be added to expressions spanning multiple lines.
  * Fixed bug where includes sometimes needed a subdirectory in their path.
  * Fixed bug where member lookups sometimes included the parent scopes.
  * Macro definition symbol offsets are now included in the symbol file.
  * Symbols with integer `equ` values are now included in the symbol file.
  * Fixed erroneous symbol redefinition error for nested directives.
  * Ternary `?:` expression operands which don’t evaluate no longer throw errors.
  * Improved handling of labels in `if` directives.
  * The `db`, `dw` and `dd` pseudo-ops now flatten nested sequences.
  * It is now possible to index into a string with the `[]` operator.
  * The line and column numbers in errors are now 1-based as is conventional.
  * A listing of the assembled source code can now be generated.
  * The `>>>` unsigned shift right operator is now supported.
  * The `'` character can now be written as `''` in character literals.
  * The `"` character can now be written as `""` in string literals.
  * Documented the `end` directive, supported since the initial release.
  * The source file encoding is changed from ISO-8859-1 to UTF-8.
  * Glass now shows version information when run without arguments.

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
  * Fixed bug when specifying macros with more than one argument.

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
