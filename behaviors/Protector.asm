        ALOC    [ROBOTI]
        ALOC    [ROBOTJ]
        ALOC    [I]
        ALOC    [J]
        ALOC    [PHASE]
        ALOC    [Walk]
        PUSH    2
        SET     [I]
        PUSH    0
        SET     [J]
        PUSH    1
        SET     [PHASE]
        PUSH    position

keepAsking:
        ASK
        JIF     keepAsking
        SET     [ROBOTI]
        SET     [ROBOTJ]
        
start:
        CALL    lookForRobot
        JIT     start
        GET     [J]
        GET     [I]         
        GET     [ROBOTJ]
        GET     [ROBOTI]                
        CALL    mvtw
        SET     [Walk]
        SET     [ROBOTI]                
        SET     [ROBOTJ]
        GET     [Walk]
        JIT     callArrive
        CALL    callAttack
        JMP     start
        
callArrive:
        CALL    arrive
        JIT     updateIJ
        JMP     start
        
callAttack:
        CALL    attack
        JIT     start
        CALL    evade
        POP
        JMP     start

updateIJ:
        GET     [I]
        GET     [J]
        EQ
        JIT     goToTheEdge
        PUSH    2
        DUP
        SET     [I]
        SET     [J]
        JMP     start
        
goToTheEdge:
        GET     [PHASE]
        JIF     goLeft
        PUSH    0
        SET     [PHASE]
        PUSH    0
        SET     [I]
        PUSH    2
        SET     [J]
        JMP     start

goLeft:
        PUSH    1
        SET     [PHASE]
        PUSH    2
        SET     [I]
        PUSH    0
        SET     [J]
        JMP     start        

lookForRobot:
        ALOC    [nRobot]
        SEE
        PUSH    Robot
        SEEK
        DUP
        JIF     donthits
        SET     [nRobot]

hit:    POP
        PUSH    1
        PUSH    (x)melee  
        HIT
        POP          
        GET     [nRobot]
        PUSH    1
        SUB
        DUP
        JIF     hits
        SET     [nRobot]
        JMP     hit
        
hits:
        POP
        PUSH    1
        JMP     freeLFR

donthits:
        POP
        PUSH    0
        JMP     freeLFR

freeLFR:
        FREE    [nRobot]
        RET
        

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
        RET
fal:    PUSH    0
        RET

attack:
        ALOC    [hit]
        PUSH    0
        SET     [hit]
        GET     [J]
        GET     [ROBOTJ]
        EQ      
        JIT     Jsame
        JMP     attackI
        
attackI: 
        GET     [J]
        GET     [ROBOTJ]
        SUB
        PUSH    0
        GT  
        JIT     attackW
        JMP     attackE        
                    
Jsame:  GET     [J]
        GET     [ROBOTJ]
        SUB
        PUSH    0
        GT  
        JIT     attackN
        JMP     attackS
        
attackN:
        GET     [ROBOTI]
        PUSH    2
        MOD
        JIT     attackNW
        JMP     attackNE
        
attackS:
        GET     [ROBOTI]
        PUSH    2
        MOD
        JIT     attackSW
        JMP     attackSE                
        
attackEHit:
        PUSH    1
        SET     [hit]
attackE:
        PUSH    ->E
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackEHit
        GET     [hit]
        FREE    [hit]
        RET               

attackNEHit:
        PUSH    1
        SET     [hit]
attackNE:
        PUSH    ->NE
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackNEHit
        GET     [hit]
        FREE    [hit]
        RET         
        
attackNWHit:
        PUSH    1
        SET     [hit]
attackNW:
        PUSH    ->NW
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackNWHit
        GET     [hit]
        FREE    [hit]
        RET          
                                   
attackWHit:
        PUSH    1
        SET     [hit]
attackW:
        PUSH    ->W
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackWHit
        GET     [hit]
        FREE    [hit]
        RET

attackSWHit:
        PUSH    1
        SET     [hit]        
attackSW:
        PUSH    ->SW
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackSWHit
        GET     [hit]
        FREE    [hit]
        RET               
                       
attackSEHit:
        PUSH    1
        SET     [hit]        
attackSE:
        PUSH    ->SE
        PUSH    1
        PUSH    (x)melee          
        HIT
        JIT     attackSEHit
        GET     [hit]
        FREE    [hit]
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
        PUSH    0
        RET

evadeL:
        PUSH    ->NW
        MOVE
        JIT     CorrectNW
        PUSH    ->SW
        MOVE
        JIT     CorrectSW
        PUSH    0
        RET
                
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
        PUSH    0
        RET

evadeD:
        PUSH    ->SW
        MOVE
        JIT     CorrectSW
        PUSH    ->SE
        MOVE
        JIT     CorrectSE
        PUSH    0
        RET

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
        PUSH    0
        RET
        
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
        PUSH    0
        RET

CorrectE:
        POP
        GET     [ROBOTJ]
        PUSH    1
        ADD
        SET     [ROBOTJ]
        PUSH    1
        RET

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
        PUSH    1
        RET
        
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
        PUSH    1
        RET
        
CorrectW:
        POP
        GET     [ROBOTJ]
        PUSH    1
        SUB
        SET     [ROBOTJ]
        PUSH    1
        RET
        
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
        PUSH    1
        RET
        
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
        PUSH    1
        RET        
