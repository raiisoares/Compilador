package compiladorl3;

public class Sintatico {

    private Lexico lexico;
    private Token token;

    public Sintatico(Lexico lexico) {
        this.lexico = lexico;
    }

    public void Sintatico(){
        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("main"))
            throw new RuntimeException("Oxe, cadê o main");

        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("("))
            throw new RuntimeException("Abre o parênteses do main cabra" );

        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals(")"))
            throw new RuntimeException("Fecha o parênteses do main cabra");

        this.token = this.lexico.nextToken();
        this.bloco();

        this.token = this.lexico.nextToken();
        this.fimDePrograma();
    }

    private void bloco() {
        if(!token.getLexema().equals("{"))
            throw new RuntimeException("Oxe, tava esperando um \"{\" pertinho de " + this.token.getLexema());

        this.token = this.lexico.nextToken();
        this.comandoGeral();

        if(!token.getLexema().equals("}"))
            throw new RuntimeException("Oxe, tava esperando um \"}\" pertinho de " + this.token.getLexema());
    }

    private void comandoGeral(){
        if(token.getLexema().equals("int") || token.getLexema().equals("float") || token.getLexema().equals("char"))
            this.declaracaoVar();
        else
            this.comandos();
    }

    private void comandos(){
        if (token.getLexema().equals("while"))
            this.iteracao();
        else if(token.getLexema().equals("if"))
            this.condicional();
        else if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR)
            this.comandosBasicos();
    }

    private void comandosBasicos() {
        this.token = this.lexico.nextToken();
        if(this.token.getTipo() == Token.TIPO_OPERADOR_ATRIBUCAO)
            this.atribuicao();
        else if (token.getLexema().equals("("))
            this.funcao();
        else
            throw new RuntimeException("Ta faltando um comando pertinho de " + this.token.getLexema());
    }

    private void declaracaoVar() {
        this.token = this.lexico.nextToken();
        if (this.token.getTipo() != Token.TIPO_IDENTIFICADOR)
            throw new RuntimeException("Tu vacilou na declaração de variável pertinho de " + this.token.getLexema());

        this.token = this.lexico.nextToken();
        if (!this.token.getLexema().equals(";") && this.token.getTipo() != Token.TIPO_OPERADOR_ATRIBUCAO)
            throw new RuntimeException("Tu vacilou na declaração de variável pertinho de " + this.token.getLexema());
        else if (this.token.getTipo() == Token.TIPO_OPERADOR_ATRIBUCAO) {
                this.atribuicao();
        } else {
            this.token = this.lexico.nextToken();
            this.comandoGeral();
        }
    }

    private void funcao() {
        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals(")"))
            throw new RuntimeException("Fecha o parênteses da função danado");

        this.token = this.lexico.nextToken();
        if (token.getLexema().equals("{"))
            this.bloco();

        this.token = this.lexico.nextToken();
        this.comandoGeral();
    }

    private void iteracao() {
        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("("))
            throw new RuntimeException("Abre o parênteses do while meu vei");

        this.expRelacional();

        if(!token.getLexema().equals(")"))
            throw new RuntimeException("Fecha o parênteses do while meu vei");

        this.token = this.lexico.nextToken();
        this.bloco();

        this.token = this.lexico.nextToken();
        this.comandoGeral();
    }

    private void condicional() {
        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("("))
            throw new RuntimeException("Abre o parênteses do if cabra");

        this.expRelacional();

        if(!token.getLexema().equals(")"))
            throw new RuntimeException("Fecha o parênteses do if cabra");

        this.token = this.lexico.nextToken();
        this.bloco();

        this.token = this.lexico.nextToken();
        if (token.getLexema().equals("else")) {
            this.token = this.lexico.nextToken();
            this.bloco();
            this.token = this.lexico.nextToken();
            this.comandoGeral();
        } else
            this.comandoGeral();
    }

    private void atribuicao(){
        this.token = this.lexico.nextToken();

        if (this.token.getLexema().equals(";"))
            throw new RuntimeException("Tu vacilou na atribuição pertinho de " + this.token.getLexema());

        if (this.token.getLexema().equals("-"))
            this.token = this.lexico.nextToken();

        this.expArit();

        if (!this.token.getLexema().equals(";"))
            throw new RuntimeException("Tu vacilou na atribuição pertinho de " + this.token.getLexema());

        this.token = this.lexico.nextToken();
        this.comandoGeral();
    }

    private void expArit(){
        if (this.token.getTipo() == Token.TIPO_REAL ||  this.token.getTipo() == Token.TIPO_INTEIRO ||
                this.token.getTipo() == Token.TIPO_CHAR || this.token.getTipo() == Token.TIPO_IDENTIFICADOR){

            this.token = this.lexico.nextToken();

            if (this.token.getLexema().equals("+") || this.token.getLexema().equals("-") || this.token.getLexema().equals("*")
                    || this.token.getLexema().equals("/")) {
                this.token = this.lexico.nextToken();
                this.expArit();
            }
        }
    }

    private void expRelacional(){
        this.token = this.lexico.nextToken();

        if (this.token.getTipo() != Token.TIPO_REAL &&  this.token.getTipo() != Token.TIPO_INTEIRO &&
                this.token.getTipo() != Token.TIPO_CHAR && this.token.getTipo() != Token.TIPO_IDENTIFICADOR)
            throw new RuntimeException("Essa expressão relacional ali pertinho de " + this.token.getLexema() + " tá errada pô");

        this.expArit();

        if (this.token.getTipo() != Token.TIPO_OPERADOR_RELACIONAL)
            throw new RuntimeException("Essa expressão relacional ali pertinho de " + this.token.getLexema() + " tá errada pô");

        this.token = this.lexico.nextToken();
        if(token.getLexema().equals(")"))
            throw new RuntimeException("Essa expressão relacional ali pertinho de " + this.token.getLexema() + " tá errada pô");

        this.expArit();
    }

    private void fimDePrograma(){
        if(this.token.getTipo() == Token.TIPO_FIM_CODIGO)
            System.out.println("O código tá massa! Arretado! Tu botou pra torar");
        else
            throw new RuntimeException("Oxe, deu bronca perto do fim");
    }
}