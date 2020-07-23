/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savioaraujo.vuttr.webservice;

import br.savioaraujo.vuttr.model.Tool;
import br.savioaraujo.vuttr.service.TagDAO;
import br.savioaraujo.vuttr.service.ToolDAO;
import br.savioaraujo.vuttr.utils.ToolUtils;
import br.savioaraujo.vuttr.utils.Utils;
import br.savioaraujo.vuttr.vo.ToolVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>Serviço RESTFULL para operaçoes sobre Tool</p>
 *
 * @author evaldosavio
 */
@Path("tool")
@Stateless
public class ToolRESTService {

    @EJB
    ToolDAO toolDAO;
    @EJB
    TagDAO tagDAO;
    private final Gson gson;

    /**
     * <p>Construtor onde eh definido o formator gson</p>
     */
    public ToolRESTService() {
        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.setPrettyPrinting().create();
    }

    /**
     * <p>Metodo responvel por retornas todas as
     * <code>Tool</code>, podendo ser passado uma tag como filtro na
     * consulta.</p>
     *
     * @param tag : Parametro para consulta, para enviar mais de uma tag bastar
     * enviar separado por virgula. Ex : Node, Java, JS
     * @return Lista contendo todas as Tools encontrada no sistemas, caso tenha
     * sido informado uma tag o retorno sera todas as Tools que possuam aquela
     * tag.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tools")
    public String listAll(@QueryParam("tag") String tag) {
        if (!Utils.isEmpty(tag)) {
            String[] tags = tag.split(",");
            return gson.toJson(ToolUtils.parseToVO(toolDAO.findByTags(tags)));
        } else {
            return gson.toJson(ToolUtils.parseToVO(toolDAO.findAll()));
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("tools")
    public Response createTool(String request) {
        if (Utils.isEmpty(request)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("request content can't be empty").build();
        }

        try {
            ToolVO teste = this.gson.fromJson(request, ToolVO.class);
            Tool tool = ToolUtils.getInstaceByToolVO(teste, this.tagDAO);
            tool = toolDAO.verifyExists(tool);
            if (tool.getId() != null) {
                return Response.status(Response.Status.CONFLICT).entity("tool already created.").build();
            } else {
                toolDAO.save(tool);
                return Response.ok(this.gson.toJson(new ToolVO(tool)), MediaType.APPLICATION_JSON).status(Response.Status.CREATED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Tool request can't be construct.").build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/tools/{id}")
    public Response deleteTool(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("id can't be null.").build();
        }
        Tool tool = toolDAO.load(id);
        if (tool != null) {
            toolDAO.inactivate(tool);
            return Response.status(204).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Tool not found").build();
        }

    }
}
