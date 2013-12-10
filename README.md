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
  `sudo bash scripts/install_ivy.bash`
* Para a instalação local:    
  `bash scripts/install_ivy.bash`

Por fim, para compilar o jogo, digite:    
                                            
    $ ant                                   

Para  criar uma  aplicação sem  dependências, 
que  possa ser  rodada independentemente  do 
diretório, é  necessário instalar localmente 
oprograma [One-Jar™][5]. Para fazê-lo, rode:

    $ bash scripts/install_one-jar.bash

Em seguida, compile com:

    $ ant jar

#### JOGO ####

O  jogo consiste  em programar uma  série de 
robôs para batalharem, num estilo de RTS 2x2.
Para  tanto,  os robôs devem  ter suas ações 
programadas. Eles irão executá-las até que o 
jogo acabe ou sejam destruídos.

Os robôs devem ser  programados numa pseudo-
linguagem de alto nível chamada __Positron__,
desenvolvida e compilada para a linguagem de
alto nível  legível pelas  máquinas virtuais 
dos robôs (chamada __Quark__).

Os programas devem  ser criados com extensão 
*.pos*.   Exemplos   estão   disponíveis  no 
diretório `test/` junto ao código-fonte. 

Opcionalmente,  programas podem ser escritos
diretamente em Assembly, com extensão *.asm*.
Para  utilizá-los, compile-os com:

    $ sh scripts/reload.sh path/para/o/arquivo.asm

Para  comportamentos  mais   elaborados  dos 
robôs,  utilize  os  scripts  disponíveis na 
pasta `behaviors/`.
                                            
Para iniciar o jogo: 
                                            
    $ java -jar dist/MAC0242-Project.jar

O Jogador e  a IA criam  seus robôs  segundo
um  temporizador.  Um  novo  robô  pode  ser 
carregado com base  em um dos comportamentos 
pré-definidos (*Carrier*, *Protector*, etc.)
ou  com  o código inicialmente  definido  no 
editor de textos.

Vence  o jogador  que  conseguir  coletar  5 
cristais e depositá-los na base inimiga.

#### DOCUMENTAÇÃO ####

A   documentação    do   código-fonte   está 
disponível no formato [Javadoc][4].

Para informações  sobre a utilização do jogo
via  interface  de linha de comando,  rode-o
com a opção **--help**.

Informações  adicionais estão disponíveis na
documentação  em *LaTeX*, presente  na pasta 
`doc/` do código-fonte.

[1]: https://github.com/renatocf
[2]: https://github.com/karinaawoki
[3]: https://github.com/Dhinihan
[4]: http://renatocf.github.io/MAC0242-PROJECT/javadoc/index.html
[5]: http://one-jar.sourceforge.net/
