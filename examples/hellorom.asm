;
; Hello World .ROM for cartridge environment
;
; Compile:
;    java -jar glass.jar hellorom.asm hellorom.rom
; Load with:
;    ExecROM, SofaRun, ROMLOAD (SCC+), OPFXSD (MegaFlashROM SD), etc.
;
; Constant definitions
CHPUT: equ 0A2H

; Compilation address
	org 04000H

; ROM header
	db "AB"     ; magic number
	dw Execute  ; program execution address
	dw 0, 0, 0, 0, 0, 0

; Program code entry point
Execute:
	ld hl,helloWorld
Loop:
	ld a,(hl)
	and a
	jr z,Finished
	call CHPUT
	inc hl
	jr Loop

; Halt program execution. Change to "ret" to return to MSX-BASIC.
Finished:
	di
	halt

; Data
helloWorld:
	db "Hello world!",0

; Padding to make the file size a multiple of 16K
; (Alternatively, include macros.asm and use ALIGN 4000H)
	ds -$ & 3FFFH
