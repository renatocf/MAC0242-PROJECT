        ALOC    [X]
        ALOC    [Y]
        ALOC    [ROBOTX]
        ALOC    [ROBOTY]
        PUSH    1       
        PUSH    1
        SET     [X]
        SET     [Y]
        PUSH    position      
        ASK
        POP
        SET    [ROBOTX]
        SET    [ROBOTY]  
            
loop:
        GET     [Y]
        GET     [X]
        GET     [ROBOTY]
        GET     [ROBOTX]                
        CALL    mvtw
        POP
        SET     [ROBOTX]                
        SET     [ROBOTY]
        JMP     loop

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
