import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        TheLastStanding jogo = new TheLastStanding("The Last Standing");
        JogadorGuerreiro jogador1 = new JogadorGuerreiro("Jogador 1");
        JogadorMistico jogador2 = new JogadorMistico("Jogador 2");

        jogador1.associarPersonagem(new Guerreiro("Guerreiro 1"));
        jogador2.associarPersonagem(new Mistico("Místico 1"));

        jogo.adicionarJogador(jogador1);
        jogo.adicionarJogador(jogador2);

        jogo.iniciarPartida();
        jogo.gerenciarJogadores();
        jogo.determinarVencedor();

        Item itemAtaqueGuerreiro = new Item(5, 0, "Guerreiro");
        Item itemDefesaMistico = new Item(0, 5, "Mistico");
    
        jogador1.adquirirItem(itemAtaqueGuerreiro);
        jogador2.adquirirItem(itemDefesaMistico);
        
    }
}

class TheLastStanding {
    private String nome;
    private List<Jogador> jogadores;

    public TheLastStanding(String nome) {
        this.nome = nome;
        this.jogadores = new ArrayList<>();
    }

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    public void iniciarPartida() {
        System.out.println("Iniciando nova partida...");
    }

    public void gerenciarJogadores() {
        for (Jogador jogador : jogadores) {
            System.out.println("Gerenciando jogador: " + jogador.getNome());
            jogador.atacar(jogadores.get(1).getPersonagem()); // Simples exemplo de ataque
        }
    }

    
       public void determinarVencedor() {
        System.out.println("\nResultados finais:");

        Jogador vencedor = null;
        int maiorPontuacao = Integer.MIN_VALUE;

        for (Jogador jogador : jogadores) {
            System.out.println("Pontuação de " + jogador.getNome() + ": " + jogador.getPontuacao());
            System.out.println("Status de " + jogador.getNome() + ": " + jogador.getStatus());

            if (jogador.getPontuacao() > maiorPontuacao) {
                maiorPontuacao = jogador.getPontuacao();
                vencedor = jogador;
            }
        }

        if (vencedor != null) {
            System.out.println("O vencedor é: " + vencedor.getNome());
        } else {
            System.out.println("Nenhum vencedor encontrado.");
        }
    }
}
abstract class Jogador {
    protected String nome;
    protected int pontuacao;
    protected String status;
    protected Personagem personagem;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacao = 0;
        this.status = "Ativo";
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void atacar(Personagem adversario) {
        if (adversario != null) {
            System.out.println(getNome() + " atacando " + adversario.getNome());
            int dano = calcularDano(adversario);
            adversario.setPontosDeVida(adversario.getPontosDeVida() - dano);
            System.out.println("Causou " + dano + " de dano!");
            System.out.println("Pontos de vida de " + adversario.getNome() + ": " + adversario.getPontosDeVida());
        } else {
            System.out.println("Adversário é nulo. Não é possível realizar o ataque.");
        }
    }

    protected abstract int calcularDano(Personagem adversario);

    public abstract void associarPersonagem(Personagem personagem);
}
class JogadorGuerreiro extends Jogador {
    private Item item;

    public JogadorGuerreiro(String nome) {
        super(nome);
    }

    public void adquirirItem(Item itemAtaqueGuerreiro) {
	}

	@Override
    public void atacar(Personagem adversario) {
        if (adversario != null) {
            System.out.println("Guerreiro " + nome + " atacando " + adversario.getNome());
            super.atacar(adversario); // Chama o método atacar da classe pai (Jogador)
        } else {
            System.out.println("Adversário é nulo. Não é possível realizar o ataque.");
        }
    }

    @Override
    protected int calcularDano(Personagem adversario) {
        int dano = getPersonagem().getPontosDeAtaque() + ((Guerreiro) getPersonagem()).getForca();
        if (item != null && "Guerreiro".equals(item.getTipoPersonagem())) {
            dano += item.getBonusAtaque();
        }

        // Adicione a lógica para aplicar o bônus de defesa
        if (item != null && "Guerreiro".equals(item.getTipoPersonagem())) {
            adversario.setPontosDeVida(adversario.getPontosDeVida() - item.getBonusDefesa());
            System.out.println("Causou " + item.getBonusDefesa() + " de dano de defesa!");
        }

        return dano;
    }

    // Adicione os getters e setters para o item
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void associarPersonagem(Personagem personagem) {
        if (personagem instanceof Guerreiro) {
            setPersonagem(personagem);
        } else {
            System.out.println("Jogador Guerreiro só pode associar um personagem do tipo Guerreiro.");
        }
    }
}

class JogadorMistico extends Jogador {
    private Item item;

    public JogadorMistico(String nome) {
        super(nome);
    }

    @Override
    public void atacar(Personagem adversario) {
        if (adversario != null) {
            System.out.println("Místico " + nome + " atacando " + adversario.getNome());
            super.atacar(adversario); // Chama o método atacar da classe pai (Jogador)
        } else {
            System.out.println("Adversário é nulo. Não é possível realizar o ataque.");
        }
    }

    @Override
    protected int calcularDano(Personagem adversario) {
        int dano = getPersonagem().getPontosDeAtaque() + ((Mistico) getPersonagem()).getInteligencia();
        if (item != null && "Mistico".equals(item.getTipoPersonagem())) {
            dano += item.getBonusAtaque();
        }
        return dano;
    }

    // Adicione um método para adquirir itens
    public void adquirirItem(Item item) {
        if (item != null && "Mistico".equals(item.getTipoPersonagem())) {
            this.item = item;
        }
    }

    @Override
    public void associarPersonagem(Personagem personagem) {
        if (personagem instanceof Mistico) {
            setPersonagem(personagem);
        } else {
            System.out.println("Jogador Místico só pode associar um personagem do tipo Místico.");
        }
    }
}


abstract class Personagem {
    protected String nome;
    protected int pontosDeVida;
    protected int pontosDeAtaque;
    protected int pontosDeDefesa;

    public Personagem(String nome) {
        this.nome = nome;
        this.pontosDeVida = 100;
        this.pontosDeAtaque = new Random().nextInt(6) + 15;
        this.pontosDeDefesa = new Random().nextInt(6) + 5;
    }

    public abstract void atacar(Personagem adversario);

    public String getNome() {
        return nome;
    }

    public int getPontosDeVida() {
        return pontosDeVida;
    }

    public void setPontosDeVida(int pontosDeVida) {
        this.pontosDeVida = pontosDeVida;
    }

    public int getPontosDeAtaque() {
        return pontosDeAtaque;
    }

    public void setPontosDeAtaque(int pontosDeAtaque) {
        this.pontosDeAtaque = pontosDeAtaque;
    }

    public int getPontosDeDefesa() {
        return pontosDeDefesa;
    }

    public void setPontosDeDefesa(int pontosDeDefesa) {
        this.pontosDeDefesa = pontosDeDefesa;
    }
}

class Guerreiro extends Personagem {
    private int forca;
    private int velocidade;

    public Guerreiro(String nome) {
        super(nome);
        this.forca = new Random().nextInt(9) + 2;
        this.velocidade = new Random().nextInt(9) + 2;
    }

    @Override
    public void atacar(Personagem adversario) {
        System.out.println("Guerreiro " + nome + " atacando " + adversario.getNome());
        // Lógica de ataque do guerreiro
    }

    public int getForca() {
        return forca;
    }

    // Adicione outros métodos e lógica conforme necessário
}

class Mistico extends Personagem {
    private int inteligencia;
    private int recuperacao;

    public Mistico(String nome) {
        super(nome);
        this.inteligencia = new Random().nextInt(9) + 2;
        this.recuperacao = new Random().nextInt(9) + 2;
    }

    @Override
    public void atacar(Personagem adversario) {
        System.out.println("Místico " + nome + " atacando " + adversario.getNome());
        // Lógica de ataque do místico
    }

    public int getInteligencia() {
        return inteligencia;
    }

    // Adicione outros métodos e lógica conforme necessário
}
class Item {
    private int bonusAtaque;
    private int bonusDefesa;
    private String tipoPersonagem;

    public Item(int bonusAtaque, int bonusDefesa, String tipoPersonagem) {
        this.bonusAtaque = bonusAtaque;
        this.bonusDefesa = bonusDefesa;
        this.tipoPersonagem = tipoPersonagem;
    }

	public int getBonusDefesa() {
		return 0;
	}

	public int getBonusAtaque() {
		return 0;
	}

	public Object getTipoPersonagem() {
		return null;
	}

    // Getters e Setters conforme necessário
}
