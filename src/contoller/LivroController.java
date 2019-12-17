package contoller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bean.Vendedor;
import model.DAO.VendedorDAO;
import model.bean.Livro;

public class LivroController {

    private Livro vendedorSelecionado;
    private List<Livro> tabelaDeLivros;
    private LivroDAO IDAO;

    public LivroController() {
        IDAO = new LivroDAO();
    }

    public void listarTodos(DefaultTableModel modeloTabela) {
        modeloTabela.setNumRows(0);
        List<Vendedor> listaVendedores = IDAO.buscarTodos();

        for (Livro v : listaLivro) {
            modeloTabela.addRow(new Object[]{ v.getISBN(),
                IDAO.getNome(), v.getAutor(), getQuantidadeDPaginas(), getCategoria);
        }
    }

    public void listarTodos(DefaultTableModel modeloTabela, int id) {
        modeloTabela.setNumRows(0);
        List<Livro>listaLivros=IDAO.buscarTodos();
         
    }
      public void listarPorCPF(DefaultTableModel modeloTabela, String CPF) {
        modeloTabela.setNumRows(0);
        Vendedor vendedorBuscado = IDAO.buscarPorCPF(CPF);
        
        modeloTabela.addRow(new Object[]{vendedorBuscado.getId(), vendedorBuscado.getCPF(),
            vendedorBuscado.getNome(), vendedorBuscado.getEndereco(), vendedorBuscado.isStatus() ? "1 - Ativo" : "2 - Inativo"});
    }
    
    public void salvar(DefaultTableModel modeloTabela, Livro livro, boolean novo ) {
        if( novo ) {
            IDAO.inserir(livro);
        } else {
            IDAO.atualizar(livro);
        }
        this.listarTodos(modeloTabela);
    }
    
    public void excluir(DefaultTableModel modeloTabela, Livro livro ) {
        System.out.println("Excluindo vendedor No.: " + Livro.getISBN());
        if( Livro.getISBN() != 0 ) {
            IDAO.excluir(Livro.getISBN());
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir as informações.\nVendedor não localizado.", "Erro ao excluir", JOptionPane.ERROR_MESSAGE);
        }
        this.listarTodos(modeloTabela);
    }

}
