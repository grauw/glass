What’s new in Glass
===================

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
