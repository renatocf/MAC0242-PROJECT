        PUSH    0
WHILE:  PUSH  "A"
        PRN
        PUSH    1
        ADD
        DUP
        PUSH    5
        EQ
        JIT   END
        JMP     1
END:    END
