# Projeto Final POO(Space Invaders)
O projeto consiste em uma implementação simplificada do famoso jogo Space Invaders, possuindo uma implementação em Java com JavaFX e outra em Python e tem o intuito de fixar os conhecimentos adquiridos ao longo da disciplina de Programação Orientada a Objetos no período letivo 2025.1 na Univerdade Federal de Sergipe.

Membros da equipe:
- Bruno Carvalho Santana Rocha
- Danilo Campos Deichmann

[Link do Repositório](https://github.com/BrunocsRocha/Space-Invaders)


## Executando a Aplicação:
### Java
Para executar a aplicação em Java é necessário ter o JDK 17 ou superior instalado, após o usuário clonar o repositório ele deve percorrer até o local onde o repositório foi armazenado e então digitar o seguinte comando:
<code>java --module-path "C:\caminho\para\javafx-sdk-21.0.7\lib" --add-modules javafx.controls,javafx.fxml SpaceInvaders.App</code>
Substitua <code>C:\caminho\para\javafx-sdk-21.0.7\lib</code> pelo caminho onde você extraiu os arquivos do JavaFX SDK no seu computador.
### Python
Para executar a aplicação em Python é preciso ter a biblioteca PyQt5 instalada, em seguida navegue até a pasta onde extraiu os arquivos e acesse a pasta "python", a qual o arquivo a ser executado está dentro dela com nome de "Space_invaders", para acessa-lo digite o comando:
<code>python Space_Invaders.py</code>

## Controles
- **← ou A**: Movimentar o player para a esquerda;
- **→ ou D**: Movimentar o player para a direita;
- **Space**: Disparar(**Em Python**);
- **Botão esquerdo do mouse**: Disparar(**Em Java**);

## Implementação

## Java
A implementação em Java começou com três arquivos principais: o Primary Controller, que gerencia a tela inicial; o Secondary Controller, responsável pela tela de jogo em si; e o Game Over Controller, que exibe a tela final. Essa estrutura inicial permitiu criar um protótipo funcional que permitiu testar as funcionalidades visuais, mas evidentemente o código precisava de uma organização melhor. Desse modo, para manter o encapsulamento de informações e reduzir a integração entre os blocos, decidimos criar as classes Player.java e Inimigo.java, cada uma cuidando de seus próprios atributos e comportamentos. Essa separação tornou o código mais modular, facilitando alterações futuras e testes.

### Python
Em Python, utilizamos a estrutura básica do jogo "Tiro ao alvo" fornecido pelo professor via Classroom. Esse código estabelece partes importantes da mecânica e logica do nosso jogo, como a janela principal, a gestão de eventos e a partir disso desenvolvemos outras funções mais específicas, como a frota de inimigos sincronizadas, a condição de fim de jogo, o "cronômetro" dos tiros do jogador, para que ficasse ainda mais próximo da jogabilidade do "Space Invaders".
Entretanto, em Python, optamos por aplicar uma regra diferente ao jogo, ao invés dos inimigos dispararem contra o jogador, os inimigos irão somente descer, mas assim que a primeira frota for destruída, a velocidade deles aumentam em 10%, e assim por diante, criando uma dificuldade ainda maior para o jogo. 
A aplicação do conceito de OO na linguagem Python trouxe algumas experiências diferentes em relação ao Java. As maiores e mais significantes foram a forma como os construtores funcionam, a flexibilidade da linguagem e o a abordagem do encapsulamento.


### Sobre os construtores:
Os construtores do Python são identificados pelo método __init__, diferente do Java onde o construtor tem que manter o mesmo nome da classe. Além disso, o this , normalmente, em Java é implícito, só sendo explícito em situações em que o parâmetro e o objeto possuem o mesmo nome, mas em Python, o "self" sempre é explícito, sendo passado como primeiro parâmetro em todas as funções. 

### Sobre a flexibilidade da liguagem:
O Python ignora o tipo do objeto, foca somente no seu comportamento, isso deixa o código mais rápido para ser desenvolvido e mais simples. Já o Java foca nos tipos dos objetos, deixando com a necessidade de criar hierarquias de classes complexas. 

### Sobre o encapsulamento:
O Java exige que os atributos sejam encapsulados, seja com public, protect entre outros, já no Python, não existe algo que restrinja o acesso a algum atributo ou variavel.



