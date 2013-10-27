        PUSH    13
        STO     102
        PUSH    1
        STO     103
        PUSH    1
        STO     104     
start:
        CALL    lookForCrystal
        PUSH    position
        ASK
        POP
        STO     100
        STO     101
        RCL     101
        RCL     100
        RCL     103
        RCL     102
        CALL    mvtw
        JIT     start
        CALL    arrive
        JIF     evade
        JMP     continue

continue:
        RCL     104
        JIT     up
        RCL     103
        PUSH    1
        EQ
        JIT     right
        JMP     left

left:
        PUSH    1
        STO     103
        PUSH    1
        STO     104
        JMP     start
        
right:
        PUSH    13
        STO     103
        PUSH    1
        STO     104
        JMP     start

up:
        RCL     102
        PUSH    3
        SUB
        STO     102
        PUSH    0
        STO     104
        JMP     start

arrive:
        RCL     100
        RCL     102
        NE
        JIT     false
        RCL     101
        RCL     103
        NE      
        JIT     false
        PUSH    1
        RET
false:  PUSH    0
        RET

evade:
        RCL     100
        RCL     102
        EQ
        JIT     sameI
        RCL     101
        RCL     103
        EQ      
        JIT     sameJ
        JMP     notInLine
        
sameI:
        RCL     101
        RCL     103
        SUB
        PUSH    0
        GT
        JIT     evadeR     
        JMP     evadeL

evadeR:
        PUSH    ->SE
        MOVE
        JIT     start
        PUSH    ->NE
        MOVE
        JIT     start
        JMP     end

evadeL:
        PUSH    ->NW
        MOVE
        JIT     start
        PUSH    ->SW
        MOVE
        JIT     start
        JMP     end
                
sameJ:
        RCL     100
        RCL     102
        SUB
        PUSH    0
        GT
        JIT     evadeD     
        JMP     evadeU

evadeU:
        PUSH    ->NE
        MOVE
        JIT     start
        PUSH    ->NW
        MOVE
        JIT     start
        JMP     end

evadeD:
        PUSH    ->SW
        MOVE
        JIT     start
        PUSH    ->SE
        MOVE
        JIT     start
        JMP     end

notInLine:
        RCL     101
        RCL     103
        SUB
        PUSH    0
        GT
        JIT     moveR     
        JMP     moveL

moveR:  
        PUSH    ->E
        MOVE
        JIT     start
        PUSH    ->SE
        MOVE
        JIT     start
        PUSH    ->NE
        MOVE
        JIT     start
        JMP     end
        
moveL:  
        PUSH    ->W
        MOVE
        JIT     start
        PUSH    ->SW
        MOVE
        JIT     start
        PUSH    ->NW
        MOVE
        JIT     start
        JMP     end

lookForCrystal:
        SEE
        PUSH    {crystal}
        SEEK
        DUP
        JIF     retlfc
        STO     1000
llfc:   POP
        CALL    printPosition
        RCL     1000
        PUSH    1
        SUB
        DUP
        JIF     retlfc
        STO     1000
        JMP     llfc     
        
retlfc: POP
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
        JIT     printRobotWPosition
        
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
        RCL     100
        PRN
        RCL     101
        PRN
        RET

printEPosition:
        POP
        PUSH    crystalxy
        PRN
        RCL     100
        PRN
        RCL     101
        PUSH    1
        ADD
        PRN
        RET
        
printNEPosition:
        POP
        PUSH    crystalxy
        PRN
        RCL     100
        PUSH    2
        MOD
        JIF     evenNE
        JMP     oddNE

printNWPosition:
        POP
        PUSH    crystalxy
        PRN
        RCL     100
        PUSH    2
        MOD
        JIF     evenNW
        JMP     oddNW
        
printWPosition:
        POP
        PUSH    crystalxy
        PRN
        RCL     100
        PRN
        RCL     101
        PUSH    1
        SUB
        PRN
        RET

printSWPosition:
        POP
        PUSH    crystalxy
        PRN
        RCL     100
        PUSH    2
        MOD
        JIF     evenSW
        JMP     oddSW
        
printSEPosition:
        POP
        PUSH    crystalxy
        PRN
        RCL     100
        PUSH    2
        MOD
        JIF     evenSE
        JMP     oddSE        
    
evenNE:
        RCL     100
        PUSH    1
        SUB
        PRN
        RCL     101
        PRN
        RET            

oddNE:
        RCL     100
        PUSH    1
        SUB
        PRN
        RCL     101
        PUSH    1
        ADD
        PRN
        RET            

evenNW:
        RCL     100
        PUSH    1
        SUB
        PRN
        RCL     101
        PUSH    1
        SUB
        PRN
        RET
        
oddNW:
        RCL     100
        PUSH    1
        SUB
        PRN
        RCL     101
        PRN
        RET            
            
evenSW:
        RCL     100
        PUSH    1
        ADD
        PRN
        RCL     101
        PUSH    1
        SUB
        PRN
        RET            

oddSW:
        RCL     100
        PUSH    1
        ADD
        PRN
        RCL     101
        PRN
        RET            

evenSE:
        RCL     100
        PUSH    1
        ADD
        PRN
        RCL     101
        PUSH    1
        RET            

oddSE:
        RCL     100
        PUSH    1
        ADD
        PRN
        RCL     101
        PUSH    1
        ADD
        PRN            
        RET
        
end:    NOP
        JMP     end


mvtw:
        STO     10
        STO     11
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
        
     
