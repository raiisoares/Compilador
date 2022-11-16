package compiladorl3;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import javax.management.RuntimeErrorException;

public class Sintatico {

    private Lexico lexico;
    private Token token;

    public Sintatico(Lexico lexico) {
        this.lexico = lexico;
    }

    public void Iniciar() {
        this.token = this.lexico.nextToken();
        this.E();
        if (this.token.getTipo() == Token.TIPO_FIM_CODIGO) {
            System.out.println("O código tá massa! Arretado! Tu botou para torar");
        }
    }

    public void E() {
        this.Tipos();
        this.El();
    }

    public void El() {
        if (this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.Operadores();
            this.Tipos();
            this.El();
        } else {
        }
    }

    public void Tipos() {
        if (this.token.getTipo() == Token.TIPO_IDENTIFICADOR | this.token.getTipo() == Token.TIPO_INTEIRO
                | this.token.getTipo() == Token.TIPO_REAL) {
                    
            this.token = this.lexico.nextToken();
        }else{
            throw new RuntimeException("Oxe, era para ser um identificador ou número pertinho de " + this.token.getLexema());
        }
    }

    public void Operadores(){
        if(this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO){
            this.token = this.lexico.nextToken();
        }else{
            throw new RuntimeException("Oxe, era para ter um operador aritmético (+,-,/,*) pertinho de " + this.token.getLexema());
        }
    }

}