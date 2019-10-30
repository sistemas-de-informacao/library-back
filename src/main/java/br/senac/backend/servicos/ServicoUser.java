package br.senac.backend.servicos;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.research.ws.wadl.HTTPMethods;

import br.senac.backend.db.dao.DaoUser;
import br.senac.backend.model.users.User;

@Path("/user")
public class ServicoUser {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response inserirUser(User user) {
		try {
			if (DaoUser.listarByNick(user.getNickname()).get(0).getNickname().equals(null) &&
					DaoUser.listarByEmail(user.getEmail()).get(0).getEmail().equals(null)) {
				DaoUser.inserir(user);
				return Response.status(Response.Status.OK).entity("Usu�rio cadastrado com sucesso.").build();
			}else {
				return Response.status(Response.Status.BAD_REQUEST).entity("Usu�rio j� existe").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.BAD_REQUEST).entity("Os dados fornecidos est�o incorretos.").build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> listarUsers() {
		try {
			return DaoUser.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void atualizarUser(User user) {
		try {
			DaoUser.atualizar(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response removerUser(@PathParam("id") Integer id) {
		try {
			DaoUser.excluir(id);
			return Response.status(Response.Status.OK).entity("Usu�rio cadastrado com sucesso.").build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("N�o foi poss�vel excluir usu�rio selecionado")
				.build();
	}

}
