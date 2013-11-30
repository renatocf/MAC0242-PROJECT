        PUSH    ->W
        DUP     
        LOOK    
        ITEM    
        PUSH    {crystal}
        EQ
        JIF     end
        DRAG    
        PUSH    1
        JIT     moveup
end:    PUSH    ->W
        MOVE    
        END
moveup: PUSH    ->NE
        MOVE    
        PUSH    ->NW
        MOVE    
        JMP     moveup
