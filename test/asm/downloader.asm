main:
        READ
        CALL verify
        CALL verify
        END

verify:
        DUP
        NIL
        JIT  3
        DUP
        PRN
        POP
        RET
