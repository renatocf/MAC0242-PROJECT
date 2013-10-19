start:  PUSH    position
        ASK
        POP     
        PUSH    14
        PUSH    14
        CALL    mvtw
        JIT     start

middle: PUSH    position
        ASK
        POP     
        PUSH    7
        PUSH    7
        CALL    mvtw
        JIT     middle

end:    PUSH    position
        ASK
        POP     
        PUSH    7
        PUSH    14
        CALL    mvtw
        JIT     end
        JMP     start
mvtw:
        STO     11
        STO     10
        STO     12
        STO     13
        RCL     12
        RCL     10
        SUB
        STO     10
        RCL     13
        RCL     11
        SUB
        STO     11
        RCL     10
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
        RCL     11
        DUP
        PUSH    0
        EQ
        JIT     ret
        PUSH    0
        GT
        JIT     e
        JMP     w


iPlus:  RCL     11
        PUSH    0
        EQ
        JIT     iPjZero
        RCL     11
        PUSH    0
        GT
        JIT     iPjNeg
        JMP     iPjPlus
        
iNeg:   PUSH    1
        PUSH    2
        SUB
        MUL
        RCL     11
        PUSH    0
        EQ
        JIT     iNjZero
        RCL     11
        PUSH    0
        GT
        JIT     iNjNeg
        JMP     iNjPlus

iPjZero:POP
        RCL     12
        PUSH    2
        MOD
        JIF     ne
        JMP     nw
       
iNjZero:POP
        RCL     12
        PUSH    2
        MOD
        JIF     se
        JMP     sw
       
iPjNeg: RCL     11
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

iPjPlus:RCL     11
        DIV
        PUSH    1
        PUSH    2
        DIV
        GT      
        JIT     w
        JMP     nw

iNjNeg: RCL     11
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

iNjPlus:RCL     11
        DIV
        PUSH    1
        PUSH    2
        DIV
        GT      
        JIT     w
        JMP     sw


        

 

e:      PUSH    ->E    
        MOVE
        RET

ne:     PUSH    ->NE    
        MOVE
        RET     

nw:     PUSH    ->NW    
        MOVE
        RET

w:      PUSH    ->W    
        MOVE
        RET

sw:     PUSH    ->SW    
        MOVE
        RET

se:     PUSH    ->SE    
        MOVE
        RET

ret:    POP
        PUSH 0
        RET



