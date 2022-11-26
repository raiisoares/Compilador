package compiladorl3;

public class Sintatico {

    private Lexico lexico;
    private Token token;

    public Sintatico(Lexico lexico) {
        this.lexico = lexico;
    }

    public void Sintatico(){
        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("main")){
            throw new RuntimeException("Oxe, cadê o main");
        }

        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("(")){
            throw new RuntimeException("Abre o parênteses do main cabra");
        }

        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals(")")){
            throw new RuntimeException("Fecha o parênteses do main cabra");
        }

        this.token = this.lexico.nextToken();
        this.bloco();

        this.token = this.lexico.nextToken();
        if(this.token.getTipo() == Token.TIPO_FIM_CODIGO){
            System.out.println("O código tá massa! Arretado! Tu botou pra torar");
        }else{
            throw new RuntimeException("Oxe, deu bronca perto do fim");
        }
    }

    private void bloco() {
        if(!token.getLexema().equals("{")){
            throw new RuntimeException("Oxe, tava esperando um \"{\" pertinho de " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();

        if(token.getLexema().equals("int") || token.getLexema().equals("float") || token.getLexema().equals("char"))
            this.declaracaoVar();
        else if (token.getLexema().equals("while") || token.getLexema().equals("if") || this.token.getTipo() == Token.TIPO_IDENTIFICADOR )
            this.comando();

        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("}")){
            throw new RuntimeException("Oxe, tava esperando um \"}\" pertinho de " + this.token.getLexema());
        }
    }

    private void declaracaoVar() {
        this.token = this.lexico.nextToken();
        if (this.token.getTipo() != Token.TIPO_IDENTIFICADOR) {
            throw new RuntimeException("Tu vacilou na declaração de variável pertinho de " + this.token.getLexema());
        }

        this.token = this.lexico.nextToken();
        if (!this.token.getLexema().equals(";"))
            throw new RuntimeException("Tu vacilou na declaração de variável pertinho de " + this.token.getLexema());

    }


    private void comando(){
        if (token.getLexema().equals("while"))
            this.iteracao();
        if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR){
            this.comandosBasicos();
        }
    }

    private void comandosBasicos() {
        this.token = this.lexico.nextToken();
        if(this.token.getTipo() == Token.TIPO_OPERADOR_ATRIBUCAO)
            this.atribuicao();
        else if (token.getLexema().equals("{"))
            this.bloco();
    }

    private void iteracao() {
        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("(")){
            throw new RuntimeException("Abre o parênteses do while cabra");
        }

        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals(")")){
            throw new RuntimeException("Fecha o parênteses do while cabra");
        }
    }

    private void atribuicao(){
        this.expArit();
    }

    private void expArit(){
        this.token = this.lexico.nextToken();
        if (this.token.getTipo() == Token.TIPO_REAL ||  this.token.getTipo() == Token.TIPO_INTEIRO ||
                this.token.getTipo() == Token.TIPO_CHAR || this.token.getTipo() == Token.TIPO_IDENTIFICADOR){
            this.token = this.lexico.nextToken();
            if (this.token.getLexema().equals("+") || this.token.getLexema().equals("-") || this.token.getLexema().equals("*") || this.token.getLexema().equals("/"))
                this.expArit();
            else if (!this.token.getLexema().equals(";"))
                throw new RuntimeException("Tu vacilou na expressão aritimetica pertinho de " + this.token.getLexema());
        }
        else
            throw new RuntimeException("Tu vacilou na expressão aritimetica pertinho de " + this.token.getLexema());
    }
}