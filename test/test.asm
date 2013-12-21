;
; Test source file
;

START: equ 100H
END: equ 900H

	org START

; ### Parser tests
;test comment
 ;test comment
test_label1:
test_label2:;test
test_label3: ;test
test_label4;test
test_label5 ;test
test_label6: exx;test
 exx ;test
    exx
	exx
	push af
test_label7: push af;test comment
	ex af,af';test comment
	ex af,af' ;test comment

; ### Expression builder tests
	exx
	and a
	ex hl,de
	ld a,(hl)
	ld a,(ix + 0)
	cp 10 + 15 * (5 - 2) + 4
	ld a,(10 + 15) * (5 - 2) + 4

	INCLUDE "test2"
	INCLUDE "include-relative/test3"
	INCLUDE "test4"

PAD: MACRO ?address
	IF $
	.ERROR Alignment
	ENDIF
	ds ?address
	ENDM

	PAD 1100H
