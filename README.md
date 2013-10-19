MAC0242-PROJECT
===============

#### INFORMAÇÔES ####
                                            
MAC 0242  -  Laboratório  de  Programação II    
IME-USP   -  Segundo   Semestre    de   2013    
Turma 45  -  Marcos Dimas Gubitoso              

Projeto:                                    
BATALHA DE ROBÔS                            

[Karina Suemi Awoki][1]              7572102    
[Renato Cordeiro Ferreira][2]        7990933    
[Vinícius Nascimento Silva][3]       7557626 

Em caso de problemas, acesse:    
<https://github.com/renatocf/MAC0242-PROJECT#>

Para a página do projeto:    
<http://renatocf.github.io/MAC0242-PROJECT/>

#### INSTALAÇÃO ####
                                            
Para compilar o jogo, digite:    
                                            
    $ ant                                   

#### JOGO ####

O  jogo consiste  em programar uma  série de 
robôs para batalharem, num estilo de RTS 2x2.
Para  tanto,  os robôs devem  ter suas ações 
programadas. Eles irão executá-las até que o 
jogo acabe ou sejam destruídos.

Nesta fase do desenvolvimento, a programação 
deve  ser  feita   em  linguagem  *Assembly*,
desenvolvida  especialmente  para  a máquina 
virtual  em *Java*. 

Os programas devem  ser criados com extensão 
*.asm*.   Exemplos   estão   disponíveis  no 
diretório `test/` junto ao código-fonte. 

Para  utilizá-los  como  programas  para  os 
robôs, compile-os com:

    $ sh reload.sh path/para/o/arquivo.asm
                                            
E para iniciar o jogo: 
                                            
    $ java -jar dist/MAC0242-Project.jar \
      programa_jogador_1 programa_jogador_2 

#### DOCUMENTAÇÃO ####

A   documentação    do   código-fonte   está 
disponível no formato [Javadoc][4].

[1]: https://github.com/renatocf
[2]: https://github.com/karinaawoki
[3]: https://github.com/Dhinihan
[4]: http://renatocf.github.io/MAC0242-PROJECT/javadoc/index.html
