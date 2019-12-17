package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.swing.JOptionPane;
import model.bean.Vendedor;
import DB.Connect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.bean.Livro;

public class LivroDAO implements iDAO<Livro> {

    private final String INSERT = "INSERT INTO Livro(Nome, ISBN, Autor, Categoria) VALUES (?, ?, ?, ?)";
    private final String UPDATE = "UPDATE Livro SET ISBN=?, NOME=?, AUTOR=?, CATEGORIA=? WHERE ID =?";
    private final String DELETE = "DELETE FROM Livro WHERE ID =?";
    private final String LISTALL = "SELECT * FROM Livro";
    private final String LISTBYID = "SELECT * FROM Livro WHERE ID=?";
    private final String LISTBYCPF = "SELECT * FROM Livro WHERE CPF=?";

    private Connect conn = null;
    private Connection conexao = null;
    private Object LivroEditado;
    private Object LivroEncontrado;

    public Livro inserir(Livro novoLivro) {
        conexao = this.getConnect().connection;
        if (novoLivro != null && conexao != null) {
            try {
                PreparedStatement transacaoSQL;
                transacaoSQL = conexao.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

                transacaoSQL.setString(1, novoLivro.getNome());
                transacaoSQL.setString(2, novoLivro.getISBN());
                transacaoSQL.setString(3, novoLivro.getAutor());
                transacaoSQL.setString(4, novoLivro.getCategoria());

                transacaoSQL.execute();
                JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso", "Registro inserido", JOptionPane.INFORMATION_MESSAGE);

                try (ResultSet generatedKeys = transacaoSQL.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        novoLivro.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Não foi possível recuperar o ID.");
                    }
                }

                conn.fechaConexao(conexao, transacaoSQL);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir o vendedor no banco de" + "dados. \n" + e.getMessage(), "Erro na transação SQL", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Os dados do vendedor não podem estar vazios.", "Vendedor não informado", JOptionPane.ERROR_MESSAGE);
        }

        return novoLivro;
    }

    public Livro atualizar(Vendedor vendedorEditado) {
        conexao = this.getConnect().connection;
        if (vendedorEditado != null && conexao != null) {
            try {
                PreparedStatement transacaoSQL;
                transacaoSQL = conexao.prepareStatement(UPDATE);

                transacaoSQL.setString(1, LivroEditado.getNome());
                transacaoSQL.setString(3, LivroEditado.getISBN());
                transacaoSQL.setBoolean(4, LivroEditado.getCategoria());

                transacaoSQL.setInt(5, LivroEditado.getISBN());

                int resultado = transacaoSQL.executeUpdate();

                if (resultado == 0) {
                    JOptionPane.showMessageDialog(null, "Não foi possível atualizar as informações", "Erro ao atualizar", JOptionPane.ERROR_MESSAGE);
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso", "Registro atualizado", JOptionPane.INFORMATION_MESSAGE);

                conn.fechaConexao(conexao, transacaoSQL);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir o Livro no banco de" + "dados. \n" + e.getMessage(), "Erro na transação SQL", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Os dados do Livro não podem estar vazios.", "Vendedor não informado", JOptionPane.ERROR_MESSAGE);
        }

        return LivroEditado;
    }

    @Override
    public void excluir(int ISBNLivro) {

        int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este vendedor?", "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0 - Sim  1 - Não
        if (confirmar == 1) {
            return;
        }
        conexao = this.getConnect().connection;
        if (conexao != null) {
            try {
                PreparedStatement transacaoSQL;
                transacaoSQL = conexao.prepareStatement(DELETE);

                transacaoSQL.setInt(1, ISBNLivro);

                boolean erroAoExcluir = transacaoSQL.execute();

                if (erroAoExcluir) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir", "Não foi possível excluir as informações", JOptionPane.ERROR_MESSAGE);
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                JOptionPane.showMessageDialog(null, "Registro excluido", "Vendedor excluido com sucesso", JOptionPane.INFORMATION_MESSAGE);

                conn.fechaConexao(conexao, transacaoSQL);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro na transação SQL", "Erro ao excluir do vendedor no banco de" + "dados. \n" + e.getMessage(), JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Problemas de conexão", "Não foi possível se conectar ao banco.", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<Livro> buscarTodos() {
        conexao = this.getConnect().connection;

        ResultSet resultado = null;
        ArrayList<Livro> Livros = new ArrayList<Livro>();

        if (conexao != null) {
            try {
                PreparedStatement transacaoSQL;
                transacaoSQL = conexao.prepareStatement(LISTALL);

                resultado = transacaoSQL.executeQuery();

                while (resultado.next()) {
                    Livro livroEncontrado = new Livro();

                    LivroEncontrado.setISBN(resultado.getInt("ISBN"));
                    LivroEncontrado.setNome(resultado.getString("nome"));
                    LivroEncontrado.setAutor(resultado.getString("Autor"));
                    LivroEncontrado.setCategoria(resultado.getString("Categoria"));
                    LivroEncontrado.setQuantidadeDPaginas(resultado.getBoolean("Quantidade de páginas"));

                    Livro.add(LivroEncontrado);
                }

                conn.fechaConexao(conexao, transacaoSQL);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro na transação SQL", "Erro ao procurar Livro no banco de" + "dados. \n" + e.getMessage(), JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Problemas de conexão", "Não foi possível se conectar ao banco.", JOptionPane.ERROR_MESSAGE);
        }

        return Livro;
    }

    