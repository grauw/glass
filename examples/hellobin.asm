;
; Hello World .BIN for MSX-BASIC environment
;
; Compile:
;    java -jar glass.jar hellobin.asm hellobin.bin
; Load from MSX-BASIC with:
;    BLOAD "HELLOBIN.BIN",R
;
; Constant definitions
CHPUT: equ 0A2H

; BLOAD header, before the ORG so that the header isn’t counted
	db 0FEH     ; magic number
	dw Begin    ; begin address
	dw End - 1  ; end address
	dw Execute  ; program execution address (for ,R option)

; Compilation address, somewhere out of the way of small basic programs
	org 0C000H
Begin:

; Program code entry point
Execute:
	ld hl,helloWorld
Loop:
	ld a,(hl)
	and a
	ret z
	call CHPUT
	inc hl
	jr Loop

; Data
helloWorld:
	db "Hello world!",0

End:
