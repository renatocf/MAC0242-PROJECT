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
                                            
Para  compilar o  jogo  a partir  do  código 
fonte, são necessários dois programas:
* Apache Ant™;
* Apache Ivy™;

Caso o Ant não esteja disponível  no sistema,
instale-o com:

    $ sudo apt-get install ant

Ou outro gerenciador de pacotes utilizado no
sistema.

Para instalar o Ivy,  foi disponibilizado um 
script em *Bash*:

* Para a instalação de sistema, utilize:    
  `sudo bash install_ivy.bash`
* Para a instalação local:    
  `bash install_ivy.bash`

Por fim, para compilar o jogo, digite:    
                                            
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

Para  comportamentos  mais   elaborados  dos 
robôs,  utilize  os  scripts  disponíveis na 
pasta `behaviors/`.
                                            
Para iniciar o jogo: 
                                            
    $ java -jar dist/MAC0242-Project.jar \
      prog1.asm prog2.asm prog3.asm 

Sendo cada um dos programas um arquivo com o
código  *Assembly*  para  os  três  robôs do 
jogador.  Eles serão  usados para o  jogador
competir contra os três robôs da máquina.

Vence  o jogador  que  conseguir  coletar  5 
cristais e depositá-los na base inimiga.

#### DOCUMENTAÇÃO ####

A   documentação    do   código-fonte   está 
disponível no formato [Javadoc][4].

[1]: https://github.com/renatocf
[2]: https://github.com/karinaawoki
[3]: https://github.com/Dhinihan
[4]: http://renatocf.github.io/MAC0242-PROJECT/javadoc/index.html
