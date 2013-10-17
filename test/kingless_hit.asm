MOVE:   PUSH ->W
        MOVE
ATK:    PUSH ->SW
        PUSH 1
        PUSH (x)MELEE
        HIT
        JIT  ATK
        END
