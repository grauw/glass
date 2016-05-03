;
; Hello World .COM for MSX-DOS environment
;
; Compile:
;    java -jar glass.jar hellocom.asm hellocom.com
; Load from MSX-DOS with:
;    HELLOCOM
;
; Constant definitions
BDOS: equ 05H
_STROUT: equ 09H

; Compilation address, standard MSX-DOS entry point
	org 100H

; Program code entry point
Execute:
	ld de,helloWorld
	ld c,_STROUT
	call BDOS
	ret

; Data
helloWorld:
	db "Hello world!$"
