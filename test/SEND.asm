msg:
    PUSH "Hello, Net"
    PUSH "Testing net"
    SEND
    JIT  msg
    PUSH "Error on message!"
    JMP  msg
