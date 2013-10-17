        PUSH ->W
        MOVE
        POP
        PUSH ->W
        MOVE
        POP
while:  SEE
        PUSH {crystal}
        SEEK
        JIF fim
        PUSH 2
        EQ 
        JIT fim
        DRAG
        POP
        PUSH ->W
        DROP
        POP
fim:    JMP while
