Glass Z80 assembler
===================

Copyright © 2013, Laurens Holst

Project information
-------------------

Glass is a cross-platform assembler for the Z80 processor, written in Java 7.

Downloads [available here](https://bitbucket.org/grauw/glass/downloads)
([changes](https://bitbucket.org/grauw/glass/src/tip/CHANGES.md)).

Bug reports and feature requests
[go here](https://bitbucket.org/grauw/glass/issues).

Author: Laurens Holst  
Contact: <laurens.nospam@grauw.nl>  
Site: <https://bitbucket.org/grauw/glass>  
License: Apache License, Version 2.0

Glass is Apache 2.0 licensed, which means that you are free to use the software
and source code in any way you wish as long as attribution is given to the
original project and its author(s). For details, please consult the LICENSE
file. To submit contributions, please contact the author.

Usage
-----

To run Glass from the command line, use the following command.

    java -jar glass.jar [OPTION] SOURCE [OBJECT] [SYMBOL]

Source specifies the source file, object the output file, and symbol a text file
which will hold a list of symbols and their addresses in the output.

Supported options:

    -I include_path

Note that [Java 7](http://java.com/getjava) must be installed to run Glass.
To check your Java version, invoke the `java -version` command.

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
    id_char = id_start_char | [0-9] | $ | '

The colon after a label is optional.

Instructions
------------

Standard z80 instruction syntax is used:

    ld a,(ix + 10)

Parentheses are used to indicate indirection.

  * Z80 instructions: `adc`, `add`, `and`, `bit`, `call`, `ccf`, `cp`, `cpd`,
    `cpdr`, `cpi`, `cpir`, `cpl`, `daa`, `dec`, `di`, `djnz`, `ei`, `ex`, `exx`,
    `halt`, `im`, `in`, `inc`, `ind`, `indr`, `ini`, `inir`, `jp`, `jr`, `ld`,
    `ldd`, `lddr`, `ldi`, `ldir`, `neg`, `nop`, `or`, `otdr`, `otir`, `out`,
    `outd`, `outi`, `pop`, `push`, `res`, `ret`, `reti`, `retn`, `rl`, `rla`,
    `rlc`, `rlca`, `rld`, `rr`, `rra`, `rrc`, `rrca`, `rrd`, `rst`, `sbc`,
    `scf`, `set`, `sla`, `sra`, `srl`, `sub`, `xor`
    
    For a complete description of the Z80 instruction set, see the official
    Zilog documentation: <http://www.zilog.com/docs/z80/um0080.pdf>
    
    In addition to the documented Z80 instructions, the variations using the
    undocumented `ixh`, `ixl`, `iyh` and `iyl` index registers are supported, as
    well as the semi-documented `in (c)`.
    
    For register jumps, `jp (hl)` etc., the parentheses are optional.
    
  * R800 instructions: `mulub`, `muluw`
    
    R800 multiplication instructions.
    
  * Define byte: `db`
    
    Defines a byte or a sequence of bytes.
    
  * Define word: `dw`
    
    Defines a word or a sequence of words.
    
  * Define double word: `dd`
    
    Defines a double word or a sequence of double words.
    
  * Define space: `ds`
    
    Defines space for a number of bytes. The first argument indicates the number
    of bytes, the optional second argument specifies the fill value (default 0).

Directives
----------

  * Origin: `org`
    
    Changes the address location counter and sets a new origin for subsequent
    statements.
    
        org 0100H
    
  * Assign constant: `equ`
    
    Assigns a constant value to a symbol.
    
        JIFFY: equ 0FC9EH
    
  * Include: `include`
    
    Includes another source file. The current working directory is searched, as
    well as any include paths specified on the command line.
    
        INCLUDE "math.asm"
    
  * Macro: `macro`, `endm`
    
    Defines a macro instruction, composed of all the instructions that follow
    until the `endm` directive is encountered. The definition’s arguments
    specify the parameters which are passed when the macro is invoked.
    
        ALIGN: MACRO ?boundary
               ds ?boundary - 1 - ($ + ?boundary - 1) % ?boundary
               ENDM
               
               ALIGN 100H
    
    All labels defined in a macro block are local.
    
  * Repetition: `rept`, `endm`
    
    Repeats a section of code a number of times. The end of the section is
    marked with the `endm` directive. The first argument is mandatory and
    specifies the number of repeats. The second argument specifies a counter
    parameter, the third the initial value for the counter (default: 0), and the
    fourth argument specifies the counter increment (default: 1).
    
        REPT 10, ?counter, 0, 2
        ld bc,(table + ?counter)
        REPT 3
        add hl,bc
        ENDR
        ENDM
    
    All labels defined in a repeat block are local.
    
  * Indefinite repetition: `irp`, `endm`
    
    Repeats a section of code for each of the arguments specified. The end of
    the section is marked with the `endm` directive. The first argument is
    mandatory and specifies the parameter the current repetition’s value is
    passed to. The remaining arguments are passed one by one as the section is
    repeated.
    
        IRP ?value, 1, 2, 4, 8, 16, 32, 64, 128
        or ?value
        ENDM
    
    All labels defined in a indefinite repeat block are local.
    
  * Procedure: `proc`, `endp`
    
    Defines a section of code as a procedure. Currently mostly serves to
    establish a local scope.
    
        shift5: PROC
                ld b,5
                jp shiftl.loop
                ENDP
                
        shiftl: PROC
                ld b,1
        loop:   add a,a
                djnz loop
                ret
                ENDP
    
    All labels defined in a procedure block are local.
    
  * Condition: `if`, `else`, `endif`
    
    Conditionally assembles a section of code, or an optional alternative
    section. The end of the section is either marked with `endif`, or with
    `else` in which case an alternative will follow up to the `endif`. The
    argument is evaluated as an integer, and if the result is nonzero (true) the
    first section is assembled, and if the result is zero (false) the
    alternative is assembled if one is provided.
    
        PAD: MACRO ?address
             IF $ > ?address
                 ERROR "Padding address exceeded."
             ELSE
                 ds ?address - $
             ENDIF
             ENDM
    
  * Error: `error`
    
    Generates an error and aborts the compilation. Optionally a message can be
    specified.
    
        ERROR "Limit exceeded."
    
  * Warning: `warning`
    
    Generates a warning. Optionally a message can be specified.
    
        WARNING "Nearly out of space."

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
