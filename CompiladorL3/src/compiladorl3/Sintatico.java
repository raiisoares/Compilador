package compiladorl3;

public class Sintatico {

    private Lexico lexico;
    private Token token;

    public Sintatico(Lexico lexico) {
        this.lexico = lexico;
    }

    public void S() {
        this.token = this.lexico.nextToken();
        this.E();
        if (this.token.getTipo() == Token.TIPO_FIM_CODIGO) {
            System.out.println("O código tá massa! Arretado! Tu botou para torar");
        }
    }

    public void E() {
        this.T();
        this.El();
    }

    public void El() {
        if (this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.OP();
            this.T();
            this.El();
        } else {
        }
    }

    public void T() {
        if (this.token.getTipo() == Token.TIPO_IDENTIFICADOR | this.token.getTipo() == Token.TIPO_INTEIRO
                | this.token.getTipo() == Token.TIPO_REAL) {
                    
            this.token = this.lexico.nextToken();
        }else{
            
        }
    }

}