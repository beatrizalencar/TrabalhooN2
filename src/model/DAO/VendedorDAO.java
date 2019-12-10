package model.DAO;

import java.util.List;
import model.bean.Vendedor;

public class VendedorDAO implements iDAO<Vendedor> {
    private final String INSERT = "INSERT INTO vendedor(CPF, NOME, ENDERECO, STATUS) VALUES (?, ?, ?, ?)";
    private final String UPDATE = "UPDATE tabela SET campo=?";
    private final String DELETE = "DELETE FROM tabela WHERE ID =?";
    private final String LISTALL = "SELECT * FROM tabela";
    private final String LISTBYID = "SELECT * FROM tabela WHERE ID=?";

    @Override
    public Vendedor inserir(Vendedor obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vendedor atualizar(Vendedor obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vendedor> buscarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vendedor buscarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
