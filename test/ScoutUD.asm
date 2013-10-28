        ALOC    [ROBOTI]
        ALOC    [ROBOTJ]
        ALOC    [I]
        ALOC    [J]
        ALOC    [VERT]
        ALOC    [Walk]
        PUSH    1
        SET     [I]
        PUSH    13
        SET     [J]
        PUSH    1
        SET     [VERT]
        PUSH    position
        ASK
        POP
        SET     [ROBOTI]
        SET     [ROBOTJ]
        
start:
        GET     [ROBOTJ]                
        GET     [ROBOTI]        
        CALL    lookForCrystal
        GET     [J]
        GET     [I]
        GET     [ROBOTJ]
        GET     [ROBOTI]                
        CALL    mvtw
        SET     [Walk]
        SET     [ROBOTI]                
        SET     [ROBOTJ]
        GET     [Walk]
        JIT     start
        JMP     arrive
back:   JIF     evade
        JMP     next

end:    NOP
        JMP     end
        
next:
        GET     [VERT]
        JIT     up
        GET     [J]
        PUSH    1
        EQ
        JIT     right
        JMP     left
        
left:
        PUSH    1
        SET     [J]
        PUSH    1
        SET     [VERT]
        JMP     start
        
right:
        PUSH    13
        SET     [J]
        PUSH    1
        SET     [VERT]
        JMP     start

up:
        GET     [I]
        PUSH    3
        ADD
        SET     [I]
        PUSH    0
        SET     [VERT]
        JMP     start
        
       

arrive:
        GET     [I]
        GET     [ROBOTI]
        NE
        JIT     fal
        GET     [J]
        GET     [ROBOTJ]
        NE      
        JIT     fal
        PUSH    1
        JMP     back
fal:    PUSH    0
        JMP     back

evade:
        GET     [I]
        GET     [ROBOTI]
        EQ
        JIT     sameI
        GET     [J]
        GET     [ROBOTJ]
        EQ      
        JIT     sameJ
        JMP     notInLine
        
sameI:
        GET     [ROBOTJ]
        GET     [J] 
        SUB
        PUSH    0
        GT
        JIT     evadeR     
        JMP     evadeL

evadeR:
        PUSH    ->SE
        MOVE
        JIT     CorrectSE
        PUSH    ->NE
        MOVE
        JIT     CorrectNE
        JMP     end

evadeL:
        PUSH    ->NW
        MOVE
        JIT     CorrectNW
        PUSH    ->SW
        MOVE
        JIT     CorrectSW
        JMP     end
                
sameJ:
        GET     [ROBOTI]
        GET     [I]
        SUB
        PUSH    0
        GT
        JIT     evadeD     
        JMP     evadeU

evadeU:
        PUSH    ->NE
        MOVE
        JIT     CorrectNE
        PUSH    ->NW
        MOVE
        JIT     CorrectNW
        JMP     end

evadeD:
        PUSH    ->SW
        MOVE
        JIT     CorrectSW
        PUSH    ->SE
        MOVE
        JIT     CorrectSE
        JMP     end

notInLine:
        GET     [ROBOTJ]
        GET     [J]
        SUB
        PUSH    0
        GT
        JIT     moveR     
        JMP     moveL

moveR:  
        PUSH    ->E
        MOVE
        JIT     CorrectE
        PUSH    ->SE
        MOVE
        JIT     CorrectSE
        PUSH    ->NE
        MOVE
        JIT     CorrectNE
        JMP     end
        
moveL:  
        PUSH    ->W
        MOVE
        JIT     CorrectW
        PUSH    ->SW
        MOVE
        JIT     CorrectSW
        PUSH    ->NW
        MOVE
        JIT     CorrectNW
        JMP     end

CorrectE:
        POP
        GET     [ROBOTJ]
        PUSH    1
        ADD
        SET     [ROBOTJ]
        JMP     start

CorrectNE:
        POP
        GET     [ROBOTI]
        PUSH    2
        MOD
        GET     [ROBOTJ]
        ADD
        SET     [ROBOTJ]
        GET     [ROBOTI]
        PUSH    1
        SUB
        SET     [ROBOTI]
        JMP     start
        
CorrectNW:
        POP
        GET     [ROBOTI]
        PUSH    2
        MOD
        GET     [ROBOTJ]
        ADD
        PUSH    1
        SUB
        SET     [ROBOTJ]
        GET     [ROBOTI]
        PUSH    1
        SUB
        SET     [ROBOTI]
        JMP     start        
        
CorrectW:
        POP
        GET     [ROBOTJ]
        PUSH    1
        SUB
        SET     [ROBOTJ]
        JMP     start
        
CorrectSW:
        POP
        GET     [ROBOTI]
        PUSH    2
        MOD
        GET     [ROBOTJ]
        ADD
        PUSH    1
        SUB
        SET     [ROBOTJ]
        GET     [ROBOTI]
        PUSH    1
        ADD
        SET     [ROBOTI]
        JMP     start
        
CorrectSE:
        POP
        GET     [ROBOTI]
        PUSH    2
        MOD
        GET     [ROBOTJ]
        ADD
        SET     [ROBOTJ]
        GET     [ROBOTI]
        PUSH    1
        ADD
        SET     [ROBOTI]
        JMP     start

lookForCrystal:
        ALOC    [nCrystal]
        ALOC    [lookI]
        ALOC    [lookJ]
        SET     [lookI]
        SET     [lookJ]        
        SEE
        PUSH    {crystal}
        SEEK
        DUP
        JIF     retlfc
        SET     [nCrystal]
llfc:   POP
        CALL    printPosition
        GET     [nCrystal]
        PUSH    1
        SUB
        DUP
        JIF     retlfc
        SET     [nCrystal]
        JMP     llfc
        
retlfc: POP
        JMP     freeLFC

freeLFC:
        FREE    [nCrystal]
        FREE    [lookI]
        FREE    [lookJ]
        RET


printPosition:
        DUP
        PUSH    ->
        EQ
        JIT     printRobotPosition

        DUP
        PUSH    ->E
        EQ
        JIT     printEPosition

        DUP
        PUSH    ->NE
        EQ
        JIT     printNEPosition

        DUP
        PUSH    ->NW
        EQ
        JIT     printNWPosition

        DUP
        PUSH    ->W
        EQ
        JIT     printWPosition
        
        DUP
        PUSH    ->SW
        EQ
        JIT     printSWPosition
        
        DUP
        PUSH    ->SE
        EQ
        JIT     printSEPosition

printRobotPosition:
        POP
        PUSH    crystalxy
        PRN
        GET     [lookI]
        PRN
        GET     [lookJ]
        PRN
        RET

printEPosition:
        POP
        PUSH    crystalxy
        PRN
        GET     [lookI]
        PRN
        GET     [lookJ]
        PUSH    1
        ADD
        PRN
        RET
        
printNEPosition:
        POP
        PUSH    crystalxy
        PRN
        GET     [lookI]
        PUSH    2
        MOD
        JIF     evenNE
        JMP     oddNE

printNWPosition:
        POP
        PUSH    crystalxy
        PRN
        GET     [lookI]
        PUSH    2
        MOD
        JIF     evenNW
        JMP     oddNW
        
printWPosition:
        POP
        PUSH    crystalxy
        PRN
        GET     [lookI]
        PRN
        GET     [lookJ]
        PUSH    1
        SUB
        PRN
        RET
        
printSWPosition:
        POP
        PUSH    crystalxy
        PRN
        GET     [lookI]
        PUSH    2
        MOD
        JIF     evenSW
        JMP     oddSW
        
printSEPosition:
        POP
        PUSH    crystalxy
        PRN
        GET     [lookI]
        PUSH    2
        MOD
        JIF     evenSE
        JMP     oddSE        
    
evenNE:
        GET     [lookI]
        PUSH    1
        SUB
        PRN
        GET     [lookJ]
        PRN
        RET

oddNE:
        GET     [lookI]
        PUSH    1
        SUB
        PRN
        GET     [lookJ]
        PUSH    1
        ADD
        PRN
        RET

evenNW:
        GET     [lookI]
        PUSH    1
        SUB
        PRN
        GET     [lookJ]
        PUSH    1
        SUB
        PRN
        RET
        
oddNW:
        GET     [lookI]
        PUSH    1
        SUB
        PRN
        GET     [lookJ]
        PRN
        RET
            
evenSW:
        GET     [lookI]
        PUSH    1
        ADD
        PRN
        GET     [lookJ]
        PUSH    1
        SUB
        PRN
        RET


oddSW:
        GET     [lookI]
        PUSH    1
        ADD
        PRN
        GET     [lookJ]
        PRN
        RET

evenSE:
        GET     [lookI]
        PUSH    1
        ADD
        PRN
        GET     [lookJ]
        PUSH    1
        RET
        
oddSE:
        GET     [lookI]
        PUSH    1
        ADD
        PRN
        GET     [lookJ]
        PUSH    1
        ADD
        PRN            
        RET
        
mvtw:
        ALOC    [RI]
        ALOC    [RJ]
        ALOC    [PI]
        ALOC    [PJ]
        ALOC    [DI]
        ALOC    [DJ]
        SET     [RI]
        SET     [RJ]
        SET     [PI]
        SET     [PJ]
        GET     [RI]
        GET     [PI]
        SUB
        SET     [DI]
        GET     [RJ]
        GET     [PJ]
        SUB
        SET     [DJ]
        GET     [DI]
        DUP     
        PUSH    0
        EQ
        JIT     iZero     
        DUP
        PUSH    0
        LT
        JIT     iPlus
        DUP
        PUSH    0
        GT
        JIT     iNeg

iZero:  POP
        GET     [DJ]
        DUP
        PUSH    0
        EQ
        JIT     ret
        PUSH    0
        GT
        JIT     e
        JMP     w


iPlus:  GET     [DJ]
        PUSH    0
        EQ
        JIT     iPjZero
        GET     [DJ]
        PUSH    0
        GT
        JIT     iPjNeg
        JMP     iPjPlus
        
iNeg:   PUSH    1
        PUSH    2
        SUB
        MUL
        GET     [DJ]
        PUSH    0
        EQ
        JIT     iNjZero
        GET     [DJ]
        PUSH    0
        GT
        JIT     iNjNeg
        JMP     iNjPlus

iPjZero:POP
        GET     [RI]
        PUSH    2
        MOD
        JIF     ne
        JMP     nw
       
iNjZero:POP
        GET     [RI]
        PUSH    2
        MOD
        JIF     se
        JMP     sw
       
iPjNeg: GET     [DJ]
        PUSH    1
        PUSH    2
        SUB
        MUL
        DIV
        PUSH    1
        PUSH    2
        DIV
        GT      
        JIT     e
        JMP     ne

iPjPlus:GET     [DJ]
        DIV
        PUSH    1
        PUSH    2
        DIV
        GT      
        JIT     w
        JMP     nw

iNjNeg: GET     [DJ]
        PUSH    1
        PUSH    2
        SUB
        MUL
        DIV
        PUSH    1
        PUSH    2
        DIV
        GT      
        JIT     e
        JMP     se

iNjPlus:GET     [DJ]
        DIV
        PUSH    1
        PUSH    2
        DIV
        GT      
        JIT     w
        JMP     sw
       
e:      PUSH    ->E    
        MOVE
        DUP
        JIT     updateE
        JMP     false

ne:     PUSH    ->NE    
        MOVE
        DUP
        JIT     updateNE
        JMP     false     

nw:     PUSH    ->NW    
        MOVE
        DUP
        JIT     updateNW
        JMP     false

w:      PUSH    ->W    
        MOVE
        DUP
        JIT     updateW
        JMP     false

sw:     PUSH    ->SW    
        MOVE
        DUP
        JIT     updateSW
        JMP     false

se:     PUSH    ->SE    
        MOVE
        DUP
        JIT     updateSE
        JMP     false

ret:    POP
false:  GET     [RJ]
        GET     [RI]
        PUSH    0
        JMP     return

updateE:
        POP
        GET     [RJ]
        PUSH    1
        ADD
        GET     [RI]
        PUSH    1
        JMP     return

updateNE:
        POP
        GET     [RI]
        PUSH    2
        MOD
        GET     [RJ]
        ADD
        GET     [RI]
        PUSH    1
        SUB
        PUSH    1
        JMP     return
        
updateNW:
        POP
        GET     [RI]
        PUSH    2
        MOD
        GET     [RJ]
        ADD
        PUSH    1
        SUB
        GET     [RI]
        PUSH    1
        SUB
        PUSH    1
        JMP     return        
        
updateW:
        POP
        GET     [RJ]
        PUSH    1
        SUB
        GET     [RI]
        PUSH    1
        JMP     return
        
updateSW:
        POP
        GET     [RI]
        PUSH    2
        MOD
        GET     [RJ]
        ADD
        PUSH    1
        SUB
        GET     [RI]
        PUSH    1
        ADD
        PUSH    1
        JMP     return        
        
updateSE:
        POP
        GET     [RI]
        PUSH    2
        MOD
        GET     [RJ]
        ADD
        GET     [RI]
        PUSH    1
        ADD
        PUSH    1
        JMP     return
                
return:
        FREE    [RI]
        FREE    [RJ]
        FREE    [PI]
        FREE    [PJ]
        FREE    [DI]
        FREE    [DJ]
        RET        
        
        
