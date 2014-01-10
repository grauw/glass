;
; Copyright 2014 Laurens Holst
;
; Licensed under the Apache License, Version 2.0 (the "License");
; you may not use this file except in compliance with the License.
; You may obtain a copy of the License at
;
;     http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.
;

;
; Pads up to a certain address.
; Gives an error message if that address is already exceeded.
;
PAD: MACRO ?address
	IF $ > ?address
		ERROR "Alignment exceeds %s"; % ?address
	ENDIF
	ds ?address - $
	ENDM

;
; Pads up to the next multiple of the specified address.
;
ALIGN: MACRO ?boundary
	ds ?boundary - 1 - ($ + ?boundary - 1) % ?boundary
	ENDM

;
; Pads to ensure a section of the given size does not cross a 100H boundary.
;
ALIGN_FIT8: MACRO ?size
	ds (($ + ?size - 1) >> 8) != ($ >> 8) && (100H - ($ & 0FFH)) || 0
	ENDM
