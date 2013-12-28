Glass Z80 assembler
===================

Copyright © 2013, Laurens Holst

Syntax
------

The assembler syntax follows common style.

Lines are composed as follows:

    label: mnemonic arguments ;comment

Note that the white space before the mnemonic is significant; otherwise, it will
be interpreted as a label.

All identifiers are case-sensitive. Mnemonics of built-in instructions,
directives, registers and flags are recognised in lowercase or uppercase,
but can not be mixed case.

Labels
------

Labels and other identifiers follow the following grammar:

    identifier = id_start_char id_char*
    id_start_char = [a-z] | [A-Z] | _ | . | ? | @
    id_char = id_start_char | [0-9] | '

The colon after a label is optional.

Instructions
------------

Standard z80 instruction syntax is used:

    ld a,(ix + 10)

Parentheses are used to indicate indirection.

In addition to the documented Z80 instructions, the variations using the
undocumented `ixh`, `ixl`, `iyh`, `iyl` index registers are supported, as well
as the semi-documented `in (c)` and the R800 `mulub` / `muluw` instructions.

For register jumps, `jp (hl)` etc., the parentheses are optional.

For a complete description of the Z80 instruction set, see the official Zilog
documentation: <http://www.zilog.com/docs/z80/um0080.pdf>

Directives
----------

  * Origin: `org`
  * Assign constant: `equ`
  * Include: `include`
  * Macro: `macro` / `endm`
  * Repetition: `rept` / `endm`
  * Indefinite repetition: `irp` / `endm`

Literals
--------

  * Decimal: `127`
  * Hexadecimal: `0FC9EH`
  * Binary: `10110001B`
  * Octal: `377O`
  * Character: `'c'`
  * String: `"abc"`

Strings support the following escape sequences:

  * `\0` (NUL)
  * `\a` (bell)
  * `\t` (tab)
  * `\n` (line feed)
  * `\f` (form feed)
  * `\r` (carriage return)
  * `\e` (escape)
  * `\"` (double quotation mark)
  * `\'` (single quotation mark)
  * `\\` (backslash)

Numeric escape sequences are not supported. In stead, you can insert them using
the comma operator: `"abc", 0FFH, "def"`.

The character set used to read files is ISO-8859-1, this maps the file’s bytes
1:1 to the Unicode code points used internally so the object code output matches
the input file bytes verbatim.

The assembler uses 32-bit integer math internally. When a 8-bit or 16-bit value
is generated, the excessive bits are usually truncated. Except for addresses,
used in jumps, calls and indirect loads, they generate an error. Index and
relative jump offsets are also checked to be in their allowed range.

Operators
---------

  * Positive: `+a`
  * Negative: `-a`
  * Complement: `~a`
  * Not: `!a`
  * Multiply: `a * b`
  * Divide: `a / b`
  * Modulo: `a % b`
  * Add: `a + b`
  * Subtract: `a - b`
  * Shift left: `a << b`
  * Shift right: `a >> b`
  * Less than: `a < b`
  * Less or equal: `a <= b`
  * Greater than: `a > b`
  * Greater or equal: `a >= b`
  * Equal: `a = b`
  * Not equal: `a != b`
  * Bitwise and: `a & b`
  * Bitwise xor: `a ^ b`
  * Bitwise or: `a | b`
  * Logical and: `a && b`
  * Logical or: `a || b`
  * Sequence: `a, b`

Logical operators use integers to represent true / false values. 0 means false,
any other value means true. They return -1 for true values.

Logical and / or apply short-circuit evaluation and evaluate to the last
evaluated value, so they can also be used similar to a ternary operator.

Operator precedence:

  * Unary `+` `-` `~` `!`
  * `*` `/` `%`
  * `+` `-`
  * `<<` `>>`
  * `<` `<=` `>` `>=`
  * `=` `!=`
  * `&`
  * `^`
  * `|`
  * `&&`
  * `||`
  * `,`
