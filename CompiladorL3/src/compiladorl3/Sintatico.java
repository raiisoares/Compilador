package compiladorl3;

public class Sintatico {

    private Lexico lexico;
    private Token token;

    public Sintatico(Lexico lexico) {
        this.lexico = lexico;
    }

    public void Programa(){
        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("main")){
            throw new RuntimeException("Oxe, cadê o main");
        }

        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("(")){
            throw new RuntimeException("Abre o parênteses do main cabra");
        }

        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("(")){
            throw new RuntimeException("Fecha o parênteses do main cabra");
        }

        this.token = this.lexico.nextToken();
        this.bloco();
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
    }
}