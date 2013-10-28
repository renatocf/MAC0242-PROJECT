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
        GET     [J]
        GET     [I]         
        GET     [ROBOTJ]                
        GET     [ROBOTI]        
        CALL    lookForCrystal
        JIT     goToTheBase
        GET     [ROBOTJ]
        GET     [ROBOTI]                
        CALL    mvtw
        SET     [Walk]
        SET     [ROBOTI]                
        SET     [ROBOTJ]
        GET     [Walk]
        JIT     start
        CALL    arrive
        JIF     callAlmostThere
        JMP     next

callAlmostThere:
        CALL    almostThere
        JIF     callEvade
        CALL    attackDestiny
        JMP     start

callEvade:
        CALL    evade
        JIT     start
        CALL    attack
        JMP     start

goToTheBase:
        PUSH    13
        DUP
        SET     [I]
        SET     [J]

run:    
        GET     [J]
        GET     [I]        
        GET     [ROBOTJ]                
        GET     [ROBOTI]        
        CALL    mvtw
        SET     [Walk]
        SET     [ROBOTI]                
        SET     [ROBOTJ]
        GET     [Walk]
        JIT     run
        CALL    arrive
        JIF     callAlmostThereII
        PUSH    ->SE
        DROP    
        JIF     run
        JMP     goBack
        
callAlmostThereII:
        CALL    almostThere
        JIF     callEvadeII
        CALL    attackDestiny
        JMP     run        
        
callEvadeII:
        CALL    evade
        JIT     run
        CALL    attack
        JMP     run
        
goBack:
        PUSH    1
        DUP
        SET     [I]
        SET     [J]

goingBack:    
        GET     [J]
        GET     [I]        
        GET     [ROBOTJ]                
        GET     [ROBOTI]        
        CALL    mvtw
        SET     [Walk]
        SET     [ROBOTI]                
        SET     [ROBOTJ]
        GET     [Walk]
        JIT     goBack
        CALL    arrive
        JIF     callAlmostThereIII
        PUSH    0
        SET     [VERT]
        JMP     start
        
callAlmostThereIII:
        CALL    almostThere
        JIF     callEvadeIII
        CALL    attackDestiny
        JMP     goingBack      
        
callEvadeIII:
        CALL    evade
        JIT     goingBack
        CALL    attack
        JMP     goingBack
        
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
        RET
fal:    PUSH    0
        RET

almostThere:
        GET     [ROBOTI]
        GET     [I]
        EQ
        JIT     almostI
        GET     [ROBOTJ]
        GET     [J]
        EQ
        JIT     almostJ
        JMP     maybeFar
        
almostI:
        GET     [ROBOTJ]
        GET     [J]
        SUB
        PUSH    1
        EQ
        JIT     nearW
        GET     [ROBOTJ]
        GET     [J]
        SUB
        PUSH    0
        PUSH    1
        SUB
        EQ
        JIT     nearE
        JMP     far

almostJ:
        GET     [ROBOTI]
        GET     [I]
        SUB
        PUSH    1
        EQ
        JIT     nearN
        GET     [ROBOTI]
        GET     [I]
        SUB
        PUSH    0
        PUSH    1
        SUB
        EQ
        JIT     nearS
        JMP     far
        
maybeFar:
        GET     [ROBOTJ]
        GET     [J]
        SUB
        PUSH    1
        EQ
        JIT     nearE
        GET     [ROBOTJ]
        GET     [J]
        SUB
        PUSH    0
        PUSH    1
        SUB
        EQ
        JIT     nearW
        JMP     far
        
                

nearN:
        GET     [ROBOTI]
        PUSH    2
        MOD
        JIT     nearNW
        JMP     nearNE
        
nearS:
        GET     [ROBOTI]
        PUSH    2
        MOD
        JIT     nearSW
        JMP     nearSE
        
        
nearE:  PUSH    ->E 
        PUSH    1
        RET
        
nearNE: PUSH    ->NE 
        PUSH    1
        RET
        
nearNW: PUSH    ->NW 
        PUSH    1
        RET

nearW:  PUSH    ->W 
        PUSH    1
        RET

nearSW: PUSH    ->SW 
        PUSH    1
        RET

nearSE: PUSH    ->SE 
        PUSH    1
        RET

far:    PUSH    0
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
        JIF     notFinded
        SET     [nCrystal]

catch:  POP
        DRAG
        JIT     cleanStack     
        GET     [nCrystal]
        PUSH    1
        SUB
        DUP
        JIF     notFinded
        SET     [nCrystal]
        JMP     catch
        
cleanStack:
        POP
        POP
        GET     [nCrystal]
        PUSH    1
        SUB
        DUP
        JIF     finded
        SET     [nCrystal]
        JMP     cleanStack
        
finded:
        POP
        PUSH    1
        JMP     freeLFC

notFinded:
        POP
        PUSH    0
        JMP     freeLFC

freeLFC:
        FREE    [nCrystal]
        FREE    [lookI]
        FREE    [lookJ]
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
        
attack:
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
        JIT     attackE
        JMP     attackW        
                    
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
        
attackE:
        PUSH    ->E
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackE
        RET               

attackNE:
        PUSH    ->NE
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackNE
        RET         
        
attackNW:
        PUSH    ->NW
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackNW
        RET                                     

attackW:
        PUSH    ->W
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackW
        RET
        
attackSW:
        PUSH    ->SW
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackSW
        RET               
                       
        
attackSE:
        PUSH    ->SE
        PUSH    1
        PUSH    (x)melee          
        HIT
        JIT     attackSE
        RET
        
attackDestiny:
attackDestinyR:
        DUP
        PUSH    1
        PUSH    (x)melee  
        HIT
        JIT     attackDestinyR
        POP
        RET         

                
