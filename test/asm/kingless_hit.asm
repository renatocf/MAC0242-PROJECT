MOVE:   PUSH ->W
        MOVE
ATK:    PUSH ->SE
        PUSH 1
        PUSH (x)MELEE
        HIT
        JIT  ATK
        END
