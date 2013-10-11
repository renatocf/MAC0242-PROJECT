begin:
        SEE
        PUSH Crystal
        SEEK
        DUP
        STO  0
        PUSH 0
        EQ
        JIT  mov
        PUSH 2
        EQ
        JIT  mac
        DRAG
        JMP  fim
        
  mac:  MOVE
        DRAG
        
  mov:  PUSH ->E
        MOVE
        JIT  begin
        JMP  back
        
  back: PUSH ->W
        MOVE
        JIT  back
        JMP  begin
        
 # pop:  RCL  0
 #       PUSH 1
 #       SUB
 #       PUSH 0
 #       EQ
 #       JIT  fim
 # loop: STO  1
 #       POP
 #       RCL  1
 #       PUSH 1
 #       SUB
 #       DUP
 #       PUSH 0
 #       EQ
 #       JIT end
 #       JMP loop  
        
  fim:  PUSH ->NW
        MOVE
        JMP  fim      
