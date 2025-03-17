import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;

class Medalhista {
    private String nome;
    private String genero;
    private LocalDate nascimento;
    private String pais;
    private String medalha;

    public Medalhista(String nome, String genero, LocalDate nascimento, String pais, String medalha) {
        this.nome = nome;
        this.genero = genero;
        this.nascimento = nascimento;
        this.pais = pais;
        this.medalha = medalha;
    }

    public String getNome() {
        return nome;
    }

    public String getMedalha() {
        return medalha;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%s, %s. Nascimento: %s. Pais: %s", nome, genero, nascimento.format(formatter), pais);
    }
}

class ListaDuplamenteEncadeada<E> {

	private Celula<E> primeiro;
	private Celula<E> ultimo;
	private int tamanho;

        
        public ListaDuplamenteEncadeada() {
            
            Celula<E> sentinela = new Celula<>();
            
            this.primeiro = this.ultimo = sentinela;
            this.tamanho = 0;
        }
        public int tamanho(){
            return this.tamanho;
        }
        public Celula<E> getUltimo(){
            return ultimo;
        }
        public Celula<E> getPrimeiro(){
            return primeiro;
        }
        
        public boolean vazia() {
            
            return (this.primeiro == this.ultimo);
        }
        
        public void inserirFinal(E novo) {
            
            Celula<E> novaCelula = new Celula<>(novo, this.ultimo, null);
            
            this.ultimo.setProximo(novaCelula);	
            this.ultimo = novaCelula;
            
            this.tamanho++;
            
        }
        
        public E removerFinal() {
            
            Celula<E> removida, penultima;
            
            if (vazia())
                throw new IllegalStateException("Não foi possível remover o último item da lista: "
                        + "a lista está vazia!");
    
            removida = this.ultimo;
                
            penultima = this.ultimo.getAnterior();
            penultima.setProximo(null);
            
            removida.setAnterior(null);
                
            this.ultimo = penultima;
                
            this.tamanho--;
                
            return (removida.getItem());
        }
        public void mesclar(ListaDuplamenteEncadeada<E> lista) {
            Celula<E> atual2 = lista.getPrimeiro().getProximo();
            Celula<E> atual = this.getPrimeiro().getProximo();
            ListaDuplamenteEncadeada<E> aux = new ListaDuplamenteEncadeada<>();
            
            while (atual != null || atual2 != null) {  // Ajustando a condição para verificar os dois
                if (atual != null) {
                    aux.inserirFinal(atual.getItem());
                    atual = atual.getProximo();
                }
        
                if (atual2 != null) {
                    aux.inserirFinal(atual2.getItem());
                    atual2 = atual2.getProximo();
                }
            }
            
            // Após mesclar, atualize a lista original com a lista mesclada (se necessário)
            this.primeiro.setProximo(aux.getPrimeiro().getProximo());
            this.ultimo = aux.getUltimo();  // Atualizando o último item
            this.tamanho = aux.tamanho();  // Atualizando o tamanho
        }
        
        
        public boolean contemSequencia(ListaDuplamenteEncadeada<E> lista) {
            Celula<E> aux = this.primeiro.getProximo(); // Ignora o nó sentinela
            Celula<E> aux2;
        
            while (aux != null) {
                aux2 = lista.primeiro.getProximo(); // Reinicia para cada tentativa
                Celula<E> temp = aux;
        
                while (aux2 != null && temp != null && temp.getItem().equals(aux2.getItem())) {
                    temp = temp.getProximo();
                    aux2 = aux2.getProximo();
                }
        
                if (aux2 == null) {
                    return true; // Encontrou a sequência
                }
        
                aux = aux.getProximo();
            }
        
            return false;
        }
        
            @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();
            Celula<E> aux = primeiro.getProximo();
            while (aux != null) {
                sb.append(aux.getItem()).append("\n");
                aux = aux.getProximo();
            }
            return sb.toString();
        }

    }

     class Celula<T> {

        private final T item;
        private Celula<T> anterior;
        private Celula<T> proximo;
    
        public Celula() {
            this.item = null;
            setAnterior(null);
            setProximo(null);
        }
    
        public Celula(T item) {
            this.item = item;
            setAnterior(null);
            setProximo(null);
        }
    
        public Celula(T item, Celula<T> anterior, Celula<T> proximo) {
            this.item = item;
            this.anterior = anterior;
            this.proximo = proximo;
        }
        
        public T getItem() {
            return item;
        }
    
        public Celula<T> getAnterior() {
            return anterior;
        }
    
        public void setAnterior(Celula<T> anterior) {
            this.anterior = anterior;
        }
        
        public Celula<T> getProximo() {
            return proximo;
        }
    
        public void setProximo(Celula<T> proximo) {
            this.proximo = proximo;
        }
    }

public class Aplicacao {

    private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Map<String, Medalhista> medalhistasMap = new HashMap<>();

    public static void main(String[] args) {
        ListaDuplamenteEncadeada<Medalhista> listaMedalhistas = new ListaDuplamenteEncadeada<>();

        carregarMedalhistas("/tmp/medallists.csv");

        Scanner scanner = new Scanner(System.in);
        String comando;
        String[] cmds; 
        while (!(comando = scanner.nextLine()).equals("FIM")) {
            cmds = comando.split(";");
            if (cmds[0].equals("INSERIR FINAL")) {
            String nome = cmds[1].trim();  // Remover espaços extras ao redor do nome
            Medalhista medalhista = medalhistasMap.get(nome);
            if (medalhista != null) {
            listaMedalhistas.inserirFinal(medalhista);
            } else {
                System.out.println("Medalhista não encontrado: " + nome);  // Mensagem de erro mais detalhada
            }

                
            } else if (cmds[0].equals("MESCLAR")) {
                int qnt = Integer.parseInt(cmds[1].trim());
                ListaDuplamenteEncadeada<Medalhista> lista = new ListaDuplamenteEncadeada<>();
                for(int i = 0; i<qnt ; i++){
                    Medalhista medalhista = medalhistasMap.get(scanner.nextLine());
                    lista.inserirFinal(medalhista);

                }
                listaMedalhistas.mesclar(lista);
                System.out.println("LISTA MESCLADA DE MEDALHISTAS");
                System.out.println(listaMedalhistas);
                listaMedalhistas = new ListaDuplamenteEncadeada<>();


                
            } else if (cmds[0].equals("CONTEM SEQUENCIA")) {
                int qnt = Integer.parseInt(cmds[1].trim());
                ListaDuplamenteEncadeada<Medalhista> lista = new ListaDuplamenteEncadeada<>();
                for (int i = 0; i < qnt; i++) {
                    Medalhista medalhista = medalhistasMap.get(scanner.nextLine());
                    lista.inserirFinal(medalhista);
                }
                boolean contem = listaMedalhistas.contemSequencia(lista);
                if (contem) {
                    System.out.println("LISTA DE MEDALHISTAS CONTEM SEQUENCIA");
                } else {
                    System.out.println("LISTA DE MEDALHISTAS NAO CONTEM SEQUENCIA");
                }
            }
            
                
            
        }
    }

private static void carregarMedalhistas(String caminhoArquivo) {
    try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo, StandardCharsets.UTF_8))) {
        String linha = br.readLine();  

        // Ignora cabeçalhos se o CSV tiver (medal_date como parte do cabeçalho)
        if (linha != null && linha.toLowerCase().contains("medal_date")) {
            linha = br.readLine();  
        }

        while (linha != null) {
            String[] dados = linha.split(",");

            // Extrair dados conforme a nova ordem do CSV
            String nome = dados[0].trim();               // name
            String medalha = dados[1].trim();            // medal_type
            String medalhaData = dados[2].trim();        // medal_date (não será utilizado diretamente aqui)
            String genero = dados[3].trim();             // gender
            String nascimentoData = dados[4].trim();    // birth_date
            String pais = dados[5].trim();              // country
            String disciplina = dados[6].trim();         // discipline
            String evento = dados[7].trim();             // event (não será utilizado diretamente aqui)

            try {
                // Fazendo o parsing da data de nascimento no formato original (yyyy-MM-dd)
                LocalDate nascimentoOriginal = LocalDate.parse(nascimentoData, INPUT_DATE_FORMAT);

                // Formatando a data de nascimento para o formato desejado (dd/MM/yyyy)
                String nascimentoFormatado = nascimentoOriginal.format(OUTPUT_DATE_FORMAT);

                // Usando a data formatada
                LocalDate nascimento = LocalDate.parse(nascimentoFormatado, OUTPUT_DATE_FORMAT);

                // Criar o objeto Medalhista com as informações extraídas
                Medalhista medalhista = new Medalhista(nome, genero, nascimento, pais, medalha);
                medalhistasMap.put(nome, medalhista);
            } catch (Exception e) {
                System.err.println("Erro ao analisar dados para " + nome + ": " + e.getMessage());
            }

            linha = br.readLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}

    
