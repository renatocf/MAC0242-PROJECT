        PUSH    0
WHILE:  PUSH  "A"
        PRN
        PUSH    1
        ADD
        DUP
        PUSH    5
        EQ
        JIT   END
        JMP   0x1
END:    END
