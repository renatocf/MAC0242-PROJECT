            PUSH    1
            DUP
            STO     0
            STO     1
            PUSH   10
            STO     2
LOOP: RCL   0
            RCL     1
            DUP
            STO     0
            ADD     
            DUP
            STO     1
            PRN
            RCL     2
            PUSH    1
            SUB
            DUP
            STO     2
            PUSH    0
            EQ
            JIF     LOOP
            END
