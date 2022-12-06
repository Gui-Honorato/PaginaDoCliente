package controllers;

import java.util.List;

import models.Cliente;
import models.DeletadoStatusCliente;
import play.mvc.Controller;

public class Clientes extends Controller{
    public static void formulario(){
        render();
    }

    public static void salvarCliente(Cliente clienteObj){
        clienteObj.save();
        listarClientes();

    }
    public static void editarCliente (Long id) {
        Cliente clienteEditObj = Cliente.findById(id);
		renderTemplate("Clientes/formulario.html", clienteEditObj);
	}
    
    public static void listarClientes(){
		
		List<Cliente> clienteListObj = Cliente.findAll();
		render(clienteListObj);
    }

    public static void removerCliente(Long id) {
        Cliente clienteRemObj = Cliente.findById(id);
        clienteRemObj.deletadoStatusClienteEnum = DeletadoStatusCliente.NEGATIVADO;
        listarClientes();
    }
    
    public static void detalharCliente(long id) {
        Cliente clienteDetObj = Cliente.findById(id);
        render(clienteDetObj);
    }
    
}
