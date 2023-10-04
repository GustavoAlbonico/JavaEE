package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.jasper.tagplugins.jstl.core.Catch;

import com.mysql.cj.protocol.Resultset;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {

	/** The diver conexao BD. */
	private String diverConexaoBD = "com.mysql.cj.jdbc.Driver";
	
	/** The url conexao BD. */
	private String urlConexaoBD = "jdbc:mysql://localhost:3306/dbagenda";
	
	/** The usuario. */
	private String usuario = "root";
	
	/** The senha. */
	private String senha = "";

	/**
	 * Gets the conexao.
	 *
	 * @return the conexao
	 */
	private Connection getConexao() {
		Connection con = null;
		try {
			Class.forName(diverConexaoBD);
			con = DriverManager.getConnection(urlConexaoBD, usuario, senha);
			return con;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	public void inserirContato(Usuario contato) {
		String create = "insert into contatos (nome,fone,email) values(?,?,?)";
		try {

			Connection con = getConexao();

			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.executeUpdate();
			pst.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	public ArrayList<Usuario> listarContatos() {

		ArrayList<Usuario> listaContatos = new ArrayList<Usuario>();
		String read = "select * from contatos order by nome;";

		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				Usuario usuario = new Usuario();
				usuario.setIdcon(rs.getInt(1));
				usuario.setNome(rs.getString(2));
				usuario.setFone(rs.getString(3));
				usuario.setEmail(rs.getString(4));

				listaContatos.add(usuario);
			}
			con.close();
			return listaContatos;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	public void selecionarContato(Usuario contato) {

		String read2 = "select * from contatos where idcon = ?";

		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(read2);

			pst.setString(1, contato.getIdcon().toString());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				contato.setIdcon(Integer.parseInt(rs.getString(1)));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}

			con.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	public void alterarContato(Usuario contato) {
		String update = "update contatos set nome=?,fone=?,email=? where idcon = ?";

		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon().toString());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	public void deletarContato(Usuario contato) {

		String delete = "delete from contatos where idcon = ?";

		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon().toString());
			pst.executeUpdate();
			con.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
