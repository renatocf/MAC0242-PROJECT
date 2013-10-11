        PUSH ->W
        DRAG
        PUSH ->W
        MOVE
        PUSH 1
        EQ
        JIF drag
        END
        
        
        
 drag:  PUSH ->W
        DROP
        PUSH 1
        EQ
        JIF drop
        JMP nope
drop:        PUSH ->E
        
        DROP
       
nope:   PUSH -> 
        MOVE
        JMP nope    
