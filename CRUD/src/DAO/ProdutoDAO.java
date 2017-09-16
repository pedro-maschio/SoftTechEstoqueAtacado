/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import factory.FabricaDeConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import mercado.Produto;

/**
 *
 * @author Pedro Maschio
 */
public class ProdutoDAO {
    private Connection conn;
    
    public void inserir(Produto produto){
        try {
            String sql = "INSERT INTO produto(nome, preco, quantidade) VALUES(?, ?, ?)";
            
            Connection conn = new FabricaDeConexao().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, produto.getNome());
            stmt.setFloat(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!");
            
        }
    }
    public List<Produto> buscar(String nome){
        List<Produto> produtos = new ArrayList<Produto>();
        try {
            String sql = "SELECT * FROM produto WHERE nome LIKE ? ORDER BY nome;";
            
            Connection conn = new FabricaDeConexao().getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, nome+"%");
            
            ResultSet rs = pstm.executeQuery();
   
            while(rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getFloat("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                
                produtos.add(produto);
            }
            pstm.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro na pesquisa, tente novamente");
        }
        return produtos;
    }
    
    public int getItens(){
        int quantidadeItens = 0;
        try {
            String sql = "SELECT sum(quantidade) FROM produto;";
            
            Connection conn = new FabricaDeConexao().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                quantidadeItens = rs.getInt("sum(quantidade)");
            }
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi posssível obter a quantidade"
                    + " total de itens em estoque.");
            throw new RuntimeException(e);      
        }
        return quantidadeItens;
    }
    public double getPatrimonio(){
        double patrimonioTotal = 0;
        try {
            List<Produto> produtos = buscar("");
            
            Produto produto = new Produto();
            
            for(int i = 0; i < produtos.size(); i++){
                produto = produtos.get(i);
                patrimonioTotal += produto.getPreco() * produto.getQuantidade();
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return patrimonioTotal;
    }
    public void exclui(int id){
        String sql = "DELETE FROM produto WHERE id = ?;";
        try {
           Connection conn = new FabricaDeConexao().getConnection();
           PreparedStatement pstm = conn.prepareStatement(sql);
           pstm.setInt(1, id);
           pstm.execute();
           pstm.close();
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Não foi possível"
                   + "concluir a compra. Erro no banco de dados.");
           throw new RuntimeException(e);
       }
       catch(Exception e){
           JOptionPane.showMessageDialog(null, "Não foi possível concluir a compra"
                   + " , tente novamente mais tarde.");
       }
    }
    
    public void atualiza(int id, int quantidade){
        String sql = "UPDATE produto SET quantidade = ? WHERE id = ?";
        
        try {
            Connection conn = new FabricaDeConexao().getConnection();
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, quantidade);
            psmt.setInt(2, id);
            
            psmt.execute();
            psmt.close();
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível concluir a compra. "
                    + "Erro no banco de dados.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Não foi possível concluir a compra. "
                    + "Tente novamente mais tarde.");
            throw new RuntimeException(e);
        }
    }
}

